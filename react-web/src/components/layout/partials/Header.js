import React from "react";

const Header = () => {
    // Assuming contextPath is available as a global variable or environment variable
    const contextPath = process.env.REACT_APP_CONTEXT_PATH || "";

    return (
        <head>
            {/* Meta Tags */}
            <meta charSet="UTF-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />

            {/* CSS Links */}
            <link
                rel="stylesheet"
                href={`${contextPath}/plugins/bootstrap/css/bootstrap-select.min.css`}
            />
            <link
                rel="stylesheet"
                href={`${contextPath}/plugins/bootstrap/css/bootstrap.min.css`}
            />
            <link
                rel="stylesheet"
                href={`${contextPath}/plugins/bootstrap/css/bootstrap-icons.css`}
            />
            <link
                rel="stylesheet"
                href={`${contextPath}/plugins/fontawesome-free-5.15.2/css/all.min.css`}
            />
            <link
                rel="stylesheet"
                href={`${contextPath}/plugins/fontawesome-free-5.15.2/css/fontawesome.min.css`}
            />
            <link
                rel="stylesheet"
                href={`${contextPath}/plugins/select2/css/select2.min.css`}
            />
            <link
                rel="stylesheet"
                href={`${contextPath}/plugins/summernote-0.8.18/summernote-bs4.min.css`}
            />
            <link
                rel="stylesheet"
                href={`${contextPath}/plugins/datetime-picker/css/jquery.datetimepicker.min.css`}
            />
            <link
                rel="shortcut icon"
                href={`${contextPath}/icons/favicon.ico`}
                type="image/x-icon"
            />
            <link
                rel="stylesheet"
                href={`${contextPath}/css/style.css`}
            />

            {/* Title */}
            <title>FinRec</title>
        </head>
    );
};

export default Header;
