import React from "react";

// Import necessary components
import Header from "./partials/Header";
import Sidebar from "./partials/SideBar";
import Footer from "./partials/Footer";

const Layout = ({content, vCenter, currentPage}) => {
    return (
        <div className={vCenter ? "v-center" : ""} data-current-page={currentPage}>
            {/* Main Layout */}
            <Header/>

            <main className="main-page">
                <div className="main-layout">
                    {/* Sidebar */}
                    <aside>
                        <Sidebar/>
                    </aside>

                    {/* Dynamic Content */}
                    <div className="content">
                        {content}
                    </div>

                    {/* Loading Icon */}
                    <div id="overlay" style={{display: "none"}}>
                        <img src="/img/gif_loading.gif" alt="Loading"/>
                    </div>
                </div>
            </main>

            {/* Footer */}
            <Footer/>
        </div>
    );
};

export default Layout;
