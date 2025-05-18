let defaultPageSize = 30;
let defaultPage = 0;
let contextPath = '';

$(document).ready(function () {

    // FinRec Header And Side Bar
    $('.sidebar-toggler').on('click', function () {
        $(".sidebar").addClass('active');
        $('.sidebar-overlay').addClass('active');
    });
    $('.sidebar-toggler-close').on('click', function () {
        $(".sidebar").removeClass('active');
        $('.sidebar-overlay').removeClass('active');
    });
    $('.overlay').on('click', function () {
        $('#error-modal').addClass('d-none').removeClass('d-flex')
    });

});

function onClickPageNo(pageValue) {
    var page = parseInt(pageValue);
    let url = window.location.href;
    let newUrl = url.replace(/page=\d+/, "page=" + page);
    location.replace(newUrl);
}

function generatePageLink(totalPages, page) {
    if (totalPages < 2) return;
    var numberOfPages = parseInt(totalPages);
    var pagination = document.querySelector(".pagination");
    let pageNo = parseInt(page);
    let count = 0;
    var pageNumber = 0;
    pagination.innerHTML = '';
    if (pageNo > 0) {
        pagination.innerHTML += '<li class="page-item"><a class="page-link custom-paging-link" href="#" text="0">1</a></li>';
        pagination.innerHTML += '<li class="page-item">' +
            '<a class="page-link custom-paging-link" href="#" text="' + (pageNo - 1) + '">back</a>' +
            '</li>';
    }
    for (pageNumber = (pageNo - 5 < 0 ? 0 : page - 4); pageNumber <= pageNo + 4 && pageNumber < numberOfPages; pageNumber += 1) {
        if (count++ === 5) break;
        if (pageNumber === pageNo) {
            pagination.innerHTML += '<li class="page-item active">' +
                '<a class="page-link custom-paging-link" href="#" text="' + pageNumber + '">' + (pageNumber + 1) + '</a>' +
                '</li>';
            continue;
        }
        pagination.innerHTML += '<li class="page-item">' +
            '<a class="page-link custom-paging-link" href="#" text="' + pageNumber + '">' + (pageNumber + 1) + '</a>' +
            '</li>';
    }
    if (pageNo < numberOfPages - 1) {
        pagination.innerHTML += '<li class="page-item"><a class="page-link custom-paging-link" href="#" text="' + (pageNo + 1) + '">next</a></li>';
        pagination.innerHTML += '<li class="page-item">' +
            '<a class="page-link custom-paging-link" href="#" text="' + (numberOfPages - 1) + '">' + numberOfPages + '</a>' +
            '</li>';

    }
}

function errorMessage(message) {
    $('#error-modal').addClass('d-flex').removeClass('d-none');
    $('#error-modal h5').text(message);
    $('.overlay').addClass('active');

}

function errorMessageHide() {
    $('#error-modal').addClass('d-none').removeClass('d-flex');
}

function getContextPath() {
    if (contextPath !== '') {
        return contextPath;
    }
    return window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
}

function getBaseUrl() {
    var base_url = location.protocol + '//' + location.hostname + (location.port ? ':' + location.port : '');
    return base_url + getContextPath();
}

function getAuthHeader() {
    return "Bearer " + Cookies.get('JSESSIONID');

}

var validation = {
    isEmailAddress: function (str) {
        var pattern = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
        return pattern.test(str);  // returns a boolean
    },
    isArrayOfEmailAddress: function (emails) {
        for (var i = 0; i < emails.length; i++) {
            if (emails[i] === "" || !this.isEmailAddress(emails[i])) {
                return false;
            }
        }
        return true;
    },
    isNotEmpty: function (str) {
        var pattern = /\S+/;
        return pattern.test(str);  // returns a boolean
    },
    isNumber: function (str) {
        var pattern = /^\s*[+-]?(\d+|\.\d+|\d+\.\d+|\d+\.)(e[+-]?\d+)?\s*$/;
        return pattern.test(str);  // returns a boolean
    },
    isSame: function (str1, str2) {
        return str1 === str2;
    }
};

function formatDecimalNumber(number) {
    number = Number(number).toFixed(2);
    return String(number).replace(/(.)(?=(\d{3})+$)/g, '$1,')
}

function downloadReport(startDate, endDate, reportType, caseText, reasonType, fileType) {
    showLoader();
    const url = getBaseUrl() + `/api/bangla-qr/${reportType}/download?startDate=${startDate}&endDate=${endDate}&fileType=${fileType}&caseText=${caseText}&reasonType=${reasonType}`;

    console.log("Downloading file for : ", url);
    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            const contentDisposition = response.headers.get('Content-Disposition');
            const fileName = contentDisposition && contentDisposition.includes('filename=')
                ? contentDisposition.split('filename=')[1].split(';')[0].trim().replace(/"/g, '')
                : 'bqr-' + reportType + '-' + startDate + '-' + endDate +'.' + fileType;

            return response.blob().then(blob => ({ blob, fileName }));
        })
        .then(({ blob, fileName }) => {
            // Create a link to trigger the download with the correct file name
            const link = document.createElement('a');
            const url = URL.createObjectURL(blob);
            link.href = url;
            link.download = fileName; // Use the file name from the server response
            link.click(); // Trigger the download
            URL.revokeObjectURL(url); // Clean up the object URL // Clean up the object URL
        })
        .catch(error => {
            alert("Report download failed");
            console.error('There was a problem with the fetch operation:', error);
        })
        .finally(f => {
            hideLoader();
    });
}

function getSummaryReportParams(startDate, endDate, caseText) {
    let dateRange = getStartAndEndDate();
    startDate = dateRange.startDate;
    endDate = dateRange.endDate;
    caseText = $('#case-text').val();
    return {startDate, endDate, caseText};
}

function getReportParams(reportType) {
    let caseText = '';
    let reasonType = '';
    let startDate = '';
    let endDate = '';
    let page = '';
    let pageSize = '';
    if (reportType === 'summary') {
        let response = getSummaryReportParams(startDate, endDate, caseText);
        startDate = response.startDate;
        endDate = response.endDate;
        caseText = response.caseText;
    } else if (reportType === 'details') {
        let  response = getDetailReportParams();
        reasonType = response.reasonType;
        caseText = response.caseText;
        startDate = response.startDate;
        endDate = response.endDate;
    }
    return {caseText, startDate, endDate, reasonType};
}

function getDetailReportParams() {
    var element = document.getElementById('bangla-qr-code-table');

    var caseText = element.getAttribute("data-caseText");
    var startDate = element.getAttribute("data-startDate");
    var endDate = element.getAttribute("data-endDate");
    var reasonType = element.getAttribute("data-reasonType");
    var page = element.getAttribute("data-page");
    var pageSize = element.getAttribute("data-pageSize");
    return {caseText, startDate, endDate, reasonType, page, pageSize};
}

function downloadFile(reportType) {
    const fileType = document.getElementById('fileType').value;
    let {caseText, startDate, endDate, reasonType} = getReportParams(reportType);
    if (fileType === 'csv') {
        downloadReport(startDate, endDate, reportType, caseText, reasonType, "csv")
    } else if (fileType === 'xls') {
        downloadReport(startDate, endDate, reportType, caseText, reasonType, "xls")
    } else if (fileType === 'xlsx') {
        downloadReport(startDate, endDate, reportType, caseText, reasonType, "xlsx")
    } else {
        alert('Invalid file format');
        return;
    }
}

function showLoader() {
    console.log("bird activated");
    document.getElementById("overlay").style.display = "flex";
}

function hideLoader() {
    console.log("bird deactivated");
    document.getElementById("overlay").style.display = "none";
}

function getStartAndEndDate() {
    let fromDate = $('#from-date').val();
    let toDate = $('#to-date').val();

    if (!fromDate || !toDate) {
        alert("Please select date range");
        return null;
    }

    let fromDateObj = new Date(fromDate);
    let toDateObj = new Date(toDate);
    if (toDateObj < fromDateObj) {
        alert("The 'to' date must be the same as or later than the 'from' date.");
        return null;
    }

    let fromDateParts = fromDate.split('-');
    let toDateParts = toDate.split('-');

    let startDate = fromDateParts[0] + fromDateParts[1] + fromDateParts[2];
    let endDate = toDateParts[0] + toDateParts[1] + toDateParts[2];

    return { startDate, endDate };
}

