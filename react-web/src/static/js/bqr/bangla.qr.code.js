$('#filter-button-summary').on('click', function () {
    initiateSummaryTableGeneration();
});

function initiateSummaryTableGeneration(pageNumber) {
    let dateRange = getStartAndEndDate();
    let startDate = '';
    let endDate = '';
    
    if (dateRange) {
        startDate = dateRange.startDate;
        endDate = dateRange.endDate;
    }

    let caseText = $('#case-text').val();
    var element = document.getElementById('bangla-qr-code-table');
    var type = element.getAttribute("data-id");

    var page = pageNumber;
    if (page == null) {
        page = defaultPage;
    }
    var pageSize = defaultPageSize;

    let getDataApiParams = type + "?startDate=" + startDate + "&endDate=" + endDate + "&page=" + page + "&pageSize=" + pageSize;
    if (caseText != null && caseText !== "") {
        getDataApiParams += "&caseText=" + caseText;
    }
    getData(getDataApiParams).then(data => {
        populateSummaryDataTable(data, startDate, endDate, page)
    });
}

function populateSummaryDataTable(data, startDate, endDate, pageNumber) {
    var context = getContextPath();
    const tableBody = document.querySelector('#bangla-qr-code-table-body');
    tableBody.innerHTML = '';

    if (data!= null && data.content != null && data.content.length > 0) {
        let page = defaultPage;
        let pageSize = defaultPageSize;
        errorMessageHide();
        var count = 1;
        let row = ``;
        data.content.forEach(report => {
            var caseDataSize = report.caseData.length;
            row = `
                    <tr>
                        <td style="align-content: center; background-color: #FFFFFF; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="${caseDataSize}" colspan="1">${count++}</td>
                        <td style="align-content: center; background-color: #FFFFFF; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="${caseDataSize}" colspan="1">
                        <a href="${context}/bangla-qr-code/details?caseText=${report.caseText}&startDate=${startDate}&endDate=${endDate}&page=${page}&pageSize=${pageSize}"
                             target="_blank">${report.caseText}</a></td>
                    `
            report.caseData.forEach((caseData) => {

                var issuer = caseData.issuer;
                var acquirer = caseData.acquirer;
                var platformFee = caseData.platformFee;
                var total = caseData.summaryTotal;
                row += `
               <td style="align-content: center; background-color: #FFFFFF; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1"><a href="${context}/bangla-qr-code/details?caseText=${report.caseText}&startDate=${startDate}&endDate=${endDate}&page=${page}&pageSize=${pageSize}&reasonType=${caseData.trxType}"
                             target="_blank">${caseData.trxType}</a></td>
               
               <td style="align-content: center; background-color: #DDD8C3; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">${issuer.bkTransactionCount}</td>
               <td style="align-content: center; background-color: #DDD8C3; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">${issuer.bbTransactionCount}</td>
               <td style="align-content: center; background-color: #DDD8C3; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">${issuer.transactionCountGap}</td>
               <td style="align-content: center; background-color: #DDD8C3; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">${issuer.bkTransactionAmount}</td>
               <td style="align-content: center; background-color: #DDD8C3; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">${issuer.bbTransactionAmount}</td>
               <td style="align-content: center; background-color: #DDD8C3; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">${issuer.transactionAmountGap}</td>
               <td style="align-content: center; background-color: #DDD8C3; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">${issuer.bkFeeAmount}</td>
               <td style="align-content: center; background-color: #DDD8C3; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">${issuer.bbFeeAmount}</td>
               <td style="align-content: center; background-color: #DDD8C3; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">${issuer.feeAmountGap}</td>
               
               <td style="align-content: center; background-color: #FFFFFF; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">${acquirer.bkTransactionCount}</td>
               <td style="align-content: center; background-color: #FFFFFF; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">${acquirer.bbTransactionCount}</td>
               <td style="align-content: center; background-color: #FFFFFF; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">${acquirer.transactionCountGap}</td>
               <td style="align-content: center; background-color: #FFFFFF; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">${acquirer.bkTransactionAmount}</td>
               <td style="align-content: center; background-color: #FFFFFF; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">${acquirer.bbTransactionAmount}</td>
               <td style="align-content: center; background-color: #FFFFFF; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">${acquirer.transactionAmountGap}</td>
               <td style="align-content: center; background-color: #FFFFFF; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">${acquirer.bkFeeAmount}</td>
               <td style="align-content: center; background-color: #FFFFFF; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">${acquirer.bbFeeAmount}</td>
               <td style="align-content: center; background-color: #FFFFFF; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">${acquirer.feeAmountGap}</td>

                <td style="align-content: center; background-color: #CFD8DC; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowSpan="1" colSpan="1">${platformFee.bkFee}</td>
                <td style="align-content: center; background-color: #CFD8DC; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowSpan="1" colSpan="1">${platformFee.bbFee}</td>
                <td style="align-content: center; background-color: #CFD8DC; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowSpan="1" colSpan="1">${platformFee.gapFee}</td>
                
                <td style="align-content: center; background-color: #DDD8C3; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowSpan="1" colSpan="1">${total.netFee}</td>
                <td style="align-content: center; background-color: #DDD8C3; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowSpan="1" colSpan="1">${total.netTrxAmount}</td>
                <td style="align-content: center; background-color: #DDD8C3; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowSpan="1" colSpan="1">${total.netAmount}</td>
                </tr>`
            });
            tableBody.innerHTML += row;
        });
        const tableFoot = document.querySelector('#bangla-qr-code-table-foot');
        tableFoot.innerHTML = '';
        let footer = `
        <tr>
            <td style="align-content: center; background-color: #ADD8E6; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="3">Grand Total</td>
            <td style="align-content: center; background-color: #ADD8E6; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">100</td>
            <td style="align-content: center; background-color: #ADD8E6; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">40</td>
            <td style="align-content: center; background-color: #ADD8E6; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">60</td>
            <td style="align-content: center; background-color: #ADD8E6; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">1,000,000.00</td>
            <td style="align-content: center; background-color: #ADD8E6; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">900,000.00</td>
            <td style="align-content: center; background-color: #ADD8E6; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">100,000.00</td>
            <td style="align-content: center; background-color: #ADD8E6; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">1,000.00</td>
            <td style="align-content: center; background-color: #ADD8E6; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">500.00</td>
            <td style="align-content: center; background-color: #ADD8E6; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">500.00</td>

            <td style="align-content: center; background-color: #ADD8E6; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">0</td>
            <td style="align-content: center; background-color: #ADD8E6; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">0</td>
            <td style="align-content: center; background-color: #ADD8E6; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">0</td>
            <td style="align-content: center; background-color: #ADD8E6; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">0</td>
            <td style="align-content: center; background-color: #ADD8E6; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">0</td>
            <td style="align-content: center; background-color: #ADD8E6; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">0</td>
            <td style="align-content: center; background-color: #ADD8E6; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">0</td>
            <td style="align-content: center; background-color: #ADD8E6; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">0</td>
            <td style="align-content: center; background-color: #ADD8E6; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">0</td>
            <td style="align-content: center; background-color: #ADD8E6; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">0</td>
            <td style="align-content: center; background-color: #ADD8E6; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">0</td>
            <td style="align-content: center; background-color: #ADD8E6; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">0</td>
            <td style="align-content: center; background-color: #ADD8E6; color: #000000; font-weight: normal; border-right: 1px solid #000000; border-left: 1px solid #000000; border-bottom: 1px solid #000000; border-top: 1px solid #000000; padding: 1px" rowspan="1" colspan="1">0</td>
        </tr>`;
        // tableFoot.innerHTML += footer;
        generateSummaryTablePageLink(data.page.totalPages, pageNumber);
    } else {
        errorMessage('No data found.');
    }
    hideLoader();
}

function generateSummaryTablePageLink(totalPages, page) {
    if (totalPages < 2) return;
    var numberOfPages = parseInt(totalPages);
    var pagination = document.querySelector(".pagination");
    let pageNo = parseInt(page);
    let count = 0;
    var pageNumber = 0;
    pagination.innerHTML = '';
    if (pageNo > 0) {
        pagination.innerHTML += '<li class="page-item"><a class="page-link custom-paging-link-summary-table" href="#" text="0">1</a></li>';
        pagination.innerHTML += '<li class="page-item">' +
            '<a class="page-link custom-paging-link-summary-table" href="#" text="' + (pageNo - 1) + '">back</a>' +
            '</li>';
    }
    for (pageNumber = (pageNo - 5 < 0 ? 0 : page - 4); pageNumber <= pageNo + 4 && pageNumber < numberOfPages; pageNumber += 1) {
        if (count++ === 5) break;
        if (pageNumber === pageNo) {
            pagination.innerHTML += '<li class="page-item active">' +
                '<a class="page-link custom-paging-link-summary-table" href="#" text="' + pageNumber + '">' + (pageNumber + 1) + '</a>' +
                '</li>';
            continue;
        }
        pagination.innerHTML += '<li class="page-item">' +
            '<a class="page-link custom-paging-link-summary-table" href="#" text="' + pageNumber + '">' + (pageNumber + 1) + '</a>' +
            '</li>';
    }
    if (pageNo < numberOfPages - 1) {
        pagination.innerHTML += '<li class="page-item"><a class="page-link custom-paging-link-summary-table" href="#" text="' + (pageNo + 1) + '">next</a></li>';
        pagination.innerHTML += '<li class="page-item">' +
            '<a class="page-link custom-paging-link-summary-table" href="#" text="' + (numberOfPages - 1) + '">' + numberOfPages + '</a>' +
            '</li>';
    }
}

var pagination = document.querySelector(".pagination");
pagination.addEventListener('click', function (event) {
    if (event.target.classList.contains('custom-paging-link-summary-table')) {
        event.preventDefault();
        let clickedPage = event.target.getAttribute('text');
        onClickPageNoGenerateSummaryData(clickedPage);
    } else if (event.target.classList.contains('custom-paging-link')) {
        event.preventDefault();
        let clickedPage = event.target.getAttribute('text');
        onClickPageNo(clickedPage);
    }
});

function onClickPageNoGenerateSummaryData(pageValue) {
    var page = parseInt(pageValue);
    initiateSummaryTableGeneration(page);
}

document.addEventListener('DOMContentLoaded', function () {
    var element = document.getElementById('bangla-qr-code-table');
    var type = element.getAttribute("data-id");

    var getDataApiParams = "";

    if (type === 'details') {
        var {caseText, startDate, endDate, reasonType, page, pageSize} = getDetailReportParams(element);

        getDataApiParams = type + "?caseText=" + caseText + "&startDate=" + startDate + "&endDate=" + endDate + "&reasonType=" + reasonType + "&page=" + page + "&pageSize=" + pageSize;
        getData(getDataApiParams).then(data => {
            populateDetailsDataTable(data, page);
        });
    }
});

function populateDetailsDataTable(data, page) {
    const tableBody = document.querySelector('#bangla-qr-code-table-body');
    tableBody.innerHTML = '';
    if (data!= null && data.content != null && data.content.length > 0) {
        errorMessageHide();
        data.content.forEach((report) => {
            const row = `
                    <tr>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.caseText}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.trxCode}</td>
                        
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.bbTrxDate}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.bbMerchantType}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.bbMerchantCountry}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.bbMerchantCity}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.bbMerchantName}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.bbMerchantId}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.bbMerchantContractNumber}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.bbMerchantMemberId}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.bbRRN}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.bbTrxAmount}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.bbSourceType}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.bbBinType}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.bbFeePercent}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.bbFeeAmount}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.bbPlatformFeeAmount}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.bbPlatformFeePercent}</td>
                        
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.cpsTrxInitiateTimestamp}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.cpsPartDate}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.cpsTrxTimestamp}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.cpsCustomerWalletNo}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.cpsMerchantPan}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.cpsMerchantName}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.cpsMerchantType}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.cpsMCC}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.cpsAcquirerName}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.cpsTrxAmount}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.cpsFeeAmount}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.cpsTrxIdBkash}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.cpsRRN}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.cpsTrxStatus}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.cpsMerchantLocation}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.cpsReasonType}</td>
                        <td style="padding: 8px; border-right: 1px solid #E8E8E8;border-bottom: 1px solid #E8E8E8;">${report.cpsTrxType}</td>
                    </tr>`;
            tableBody.innerHTML += row;
        });
        generatePageLink(data.page.totalPages, page);
    } else {
        errorMessage('No data found.');
    }
    hideLoader();
}

function getData(apiParams) {
    showLoader();
    var url = getBaseUrl() + "/api/bangla-qr/" + apiParams
    console.log("retrieving data for : " + url);
    return fetch(url)
        .then(response => {
            if (response.status === 200) {
                return response.json();
            } else {
                alert('Data fetch failed.');
            }
            return null;
        })
        .catch(error => {
            alert('Data fetch failed.');
            console.error('Error fetching data:', error);
        });
}

$('.time-picker').datetimepicker({
    maxDate: new Date(),
    datepicker: true,
    timepicker: false,
    format: 'Y-m-d',
    onChangeDateTime: function (date) {
        var today = new Date();
        if (date !== null && date.getDate() === today.getDate()) {
            this.setOptions({maxTime: new Date()});
        } else {
            this.setOptions({maxTime: false});
        }
    }
});