import React from "react";

const Sidebar = ({ currentPage, user, roles, appVersion }) => {
    // Helper function to toggle submenu visibility
    const toggleSubMenu = (id) => {
        const element = document.getElementById(id);
        if (element) {
            element.style.display = element.style.display === "block" ? "none" : "block";
        }
    };

    return (
        <div>
            {/* Mobile Header */}
            <div className="mobile-header">
                <a className="sidebar-toggler" href="#">
                    <i className="fas fa-bars"></i>
                </a>
                <div className="mobile-view-logo">
                    <img src="/img/logo-sidebar.svg" alt="bKash Logo" />
                </div>
            </div>

            {/* Sidebar */}
            <aside className="sidebar">
                {/* Sidebar Header */}
                <div className="sidebar-header">
                    <a href="/">
                        <img src="/img/logo-sidebar.svg" alt="bKash Logo" />
                    </a>
                    <button type="button" className="sidebar-toggler-close btn">
                        <i className="fas fa-times"></i>
                    </button>
                </div>

                {/* Main Menu */}
                <ul className="sidebar-main-menu">
                    {roles.some((role) => ["ROLE_VIEW", "ROLE_EDIT"].includes(role)) && (
                        <li>
                            <a href="#" onClick={() => toggleSubMenu("reconciliation-menu")}>
                                Reconciliation
                            </a>
                            <ul
                                className="sidebar-menu"
                                id="reconciliation-menu"
                                style={{
                                    display:
                                        currentPage === "bangla-qr-code-details" ||
                                        currentPage === "bangla-qr-code-summary" ||
                                        currentPage === "bangla-qr-code-upload"
                                            ? "block"
                                            : "none",
                                }}
                            >
                                <li>
                                    <a href="#" onClick={() => toggleSubMenu("bangla-qr-code-submenu")}>
                                        Bangla QR Code
                                    </a>
                                    <ul
                                        className="sub-sidebar-menu"
                                        id="bangla-qr-code-submenu"
                                        style={{
                                            display:
                                                currentPage === "bangla-qr-code-details" ||
                                                currentPage === "bangla-qr-code-summary" ||
                                                currentPage === "bangla-qr-code-upload"
                                                    ? "block"
                                                    : "none",
                                        }}
                                    >
                                        <li>
                                            <a
                                                className={currentPage === "bangla-qr-code-summary" ? "active" : ""}
                                                href="/bangla-qr-code/summary"
                                            >
                                                Summary
                                            </a>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </li>
                    )}

                    {roles.some((role) => ["ROLE_ADD", "ROLE_DELETE"].includes(role)) && (
                        <li>
                            <a
                                className={currentPage === "user" ? "active" : ""}
                                href="/user-management?page=0&size=30"
                            >
                                User Management
                            </a>
                        </li>
                    )}
                </ul>

                {/* Sidebar Footer */}
                <div className="sidebar-footer">
                    <a href="/logout">Log Out</a>
                    <br />
                    <span style={{ fontSize: "12px" }} className="sidebar site-color">
            {user.name}
          </span>{" "}
                    -{" "}
                    {roles.map((role, index) => (
                        <span key={index} style={{ fontSize: "12px" }} className="sidebar site-color">
              {role}
            </span>
                    ))}
                    <br />
                    <span style={{ fontSize: "10px" }}>v. {appVersion}</span>
                </div>
            </aside>
        </div>
    );
};

export default Sidebar;
