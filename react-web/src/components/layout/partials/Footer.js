import React from "react";

const Footer = () => {
    // Assuming contextPath is available as a global variable or environment variable
    const contextPath = process.env.REACT_APP_CONTEXT_PATH || "";

    return (
        <footer>
            {/* Including jQuery and Bootstrap JS */}
            <script src={`${contextPath}/plugins/jquery-3.5.1/jquery-3.5.1.min.js`}></script>
            <script src={`${contextPath}/plugins/bootstrap/js/bootstrap.bundle.min.js`}></script>

            {/* Bootstrap Select */}
            <script src={`${contextPath}/plugins/bootstrap/js/bootstrap-select.min.js`}></script>

            {/* Summernote JS */}
            <script src={`${contextPath}/plugins/summernote-0.8.18/summernote-bs4.min.js`}></script>

            {/* Select2 JavaScript */}
            <script src={`${contextPath}/plugins/select2/js/select2.min.js`}></script>

            {/* Datetime Picker */}
            <script src={`${contextPath}/plugins/datetime-picker/js/jquery.datetimepicker.full.min.js`}></script>

            {/* Handlebars and other scripts */}
            <script src={`${contextPath}/plugins/handlebars-v4.7.7.js`}></script>
            <script src={`${contextPath}/plugins/js.cookie.min.js`}></script>
            <script src={`${contextPath}/js/main.page.js`}></script>
            <script src={`${contextPath}/js/side.bar.js`}></script>
            <script src={`${contextPath}/plugins/xls/xlsx.full.min.js`}></script>
        </footer>
    );
};

export default Footer;
