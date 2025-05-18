function isInputValueValidForUser(requestObject) {
    console.log(requestObject);
    return requestObject.email && requestObject.roleId;
}

function addUser(base_url, auth_header, requestObject, msg) {
    showLoader();
    var requestUrl = base_url + "/user/add";

    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        crossDomain: true,
        url: requestUrl,
        headers: {
            "Authorization": auth_header,
        },
        data: JSON.stringify(requestObject),
        cache: false,
        success: function (response) {
            alert(response);
            hideLoader();
            location.reload();
        },
        error: function (error) {
            console.log(error.responseJSON)
            alert("User " + msg + " failed!");
            hideLoader();
        }
    });
}

function deleteUser(base_url, auth_header, requestObject) {
    showLoader();
    var requestUrl = base_url + "/user/remove";

    $.ajax({
        type: "DELETE",
        contentType: "application/json; charset=utf-8",
        crossDomain: true,
        url: requestUrl,
        headers: {
            "Authorization": auth_header,
        },
        data: JSON.stringify(requestObject),
        cache: false,
        success: function (response) {
            alert(response);
            hideLoader();
            location.reload();
        },
        error: function (error) {
            console.log(error.responseJSON);
            alert("User delete failed!")
            hideLoader();
        }
    });
}

function searchForUserByEmail(base_url, auth_header, email) {
    var requestUrl = base_url + "/user/search-ldap-user";

    $.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        crossDomain: true,
        url: requestUrl,
        headers: {
            "Authorization": auth_header,
        },
        data: {
            "email": email
        },
        cache: false,
        success: function (response) {
            if (response.email === email) {
                let msg = "User already exist with this email " + email;
                console.log(msg);
                errorMessage(msg);
                $("#add-email-id").val("");
            } else {
                $('#add-email-id').val(email);
            }
        },
        error: function (error) {
            errorMessage(error.responseJSON);
        }
    });
}

$(document).ready(function () {

    $('.error-text').hide();

    var base_url = getBaseUrl();
    var auth_header = getAuthHeader();

    var globalSelectedObject = {};

    function getUserEmailAndRoleId(id) {
        var userEmailIdInputSelector = "#userEmailIdInput" + id;
        var currentRole = "#currentRole" + id;

        globalSelectedObject['email'] = $(userEmailIdInputSelector).val();
        globalSelectedObject['roleId'] = $(currentRole).val();

        return globalSelectedObject;
    }

    $('.manage-search').on('click paste keypress keyup', function (event) {

        let parent = $(this).parent();
        let email = $(this).val();
        if (email.length > 0) {
            parent.find("i").removeClass('d-flex').addClass('d-none');
        } else {
            parent.find("i").removeClass('d-none').addClass('d-flex');
        }

        var keycode = (event.keyCode ? event.keyCode : event.which);
        if (keycode === '13' || keycode === 13) {
            location.replace(base_url + "/user-management?page=0&size=7&email=" + email);
            event.preventDefault();
        }
    });

    $('#ldap-user-id').on('click paste keyup keypress', function (event) {

        var email = $(this).val();
        errorMessageHide();
        searchForUserByEmail(base_url, auth_header, email);
    });

    $('#add-user-confirm').on('click', function () {

        globalSelectedObject['roleId'] = $('input[name="role"]:checked').val();
        globalSelectedObject['email'] = $('input[id="add-email-id"]').val();

        var requestObject = {
            "email": globalSelectedObject.email,
            "roleId": Number.parseInt(globalSelectedObject.roleId)
        };

        if (isInputValueValidForUser(requestObject)) {
            addUser(base_url, auth_header, requestObject, "add");
        } else {
            errorMessage("Please re-check all input fields");
        }

    });

    $('.edit-user-confirm').on('click', function () {

        var requestObject = {
            "email": globalSelectedObject.email,
            "roleId": globalSelectedObject.roleId
        };

        if (isInputValueValidForUser(requestObject)) {
            addUser(base_url, auth_header, requestObject, "edit");
        } else {
            errorMessage("Please re-check all input fields");
        }

    });

    // User add Modal
    $('#actionBtnAddUser').on('click', function () {
        $('.user-management-useradd-modal').removeClass('d-none').addClass('d-flex');
        $('.overlay').addClass('active');
    });

    // User Edit Modal
    $('.user-management-action .user-edit').on('click', function () {
        var id = $(this).parent().children("input:nth-child(1)").val();
        getUserEmailAndRoleId(id);
        $(this).parent().find(".usaction-texter-management-useredit-modal").removeClass('d-none').addClass('d-flex');
        $(this).parent().find('.overlay').addClass('active');
    });

    $('.user-management-action .user-delete').on('click', function () {
        var id = $(this).parent().children("input:nth-child(1)").val();
        getUserEmailAndRoleId(id);
        var isConfirmed = confirm("Do you want to delete the user with email " + globalSelectedObject.email + " ?")
        var requestObject = {
            "email": globalSelectedObject.email,
            "roleId": Number.parseInt(globalSelectedObject.roleId)
        };
        if (isConfirmed) {
            deleteUser(base_url, auth_header, requestObject);
        }
    });

    $('.overlay').on('click', function () {
        $('.user-management-useradd-modal').removeClass('d-flex').addClass('d-none');
        $('.overlay').removeClass('active');
        location.reload();
    });

    $(".custom-paging-link").on('click', function (event) {
        event.preventDefault();
        var page = parseInt($(this).text()) - 1;
        location.replace(base_url + "/user-management?page=" + page + "&size=7");
    });

    $('.overlay').on('click', function () {
        $('.usaction-texter-management-useredit-modal').removeClass('d-flex').addClass('d-none');
        $('.overlay').removeClass('active');
        location.reload();
    });

    $('.editRole').on('click', function () {
        globalSelectedObject['roleId'] = $(this).val();
    });

});