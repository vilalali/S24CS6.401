import { useState } from "react";
import { FiMenu } from "react-icons/fi";
import { Link, useLocation } from "react-router-dom";
import Logo from "../../assets/logoAudioMitra.png";

const Nav = () => {
  const [open, setOpen] = useState(false);
  const location = useLocation();

  return (
    <>
      <header
        className="border-b border-gray-300 py-1 sticky self-start mb-3"
        style={{
          top: 0,
          left: 0,
          right: 0,
          background: "#ffffff",
          zIndex: 1,
          boxShadow: "0 2px 5px rgba(0, 0, 0, 0.1)"
        }}
      >
        <div className="flex justify-between items-center xl:max-w-7xl xl:mx-auto max-w-full px-1 flex-wrap">
          <Link to="/">
            <div className="flex justify-center">
              <img src={Logo} alt="logo" className="cursor-pointer mr-4 h-16" />
            </div>
          </Link>

          <FiMenu
            className="lg:hidden block h-6 w-6 cursor-pointer"
            onClick={() => setOpen(!open)}
          />
          <nav
            className={`${
              open ? "block" : "hidden"
            } lg:flex lg:items-center lg:w-auto w-full`}
          >
            <ul className="text-base text-gray-600 lg:flex lg:justify-between">
              <li
                className={`lg:px-5 py-2 hover:text-blue-500 font-semibold ${
                  location.pathname === "/" ? "text-green-700" : ""
                }`}
              >
                <Link to="/">Home</Link>
              </li>
              <li
                className={`lg:px-5 py-2 hover:text-blue-500 font-semibold ${
                  location.pathname === "/converter" ? "text-green-700" : ""
                }`}
              >
                <Link to="/converter">Converter</Link>
              </li>
              <li
                className={`lg:px-5 py-2 hover:text-blue-500 font-semibold ${
                  location.pathname === "/about" ? "text-green-700" : ""
                }`}
              >
                <Link to="/about">About Us</Link>
              </li>
              <li
                className={`lg:px-5 py-2 hover:text-blue-500 font-semibold ${
                  location.pathname === "/contact" ? "text-green-700" : ""
                }`}
              >
                <Link to="/contact">Contact Us</Link>
              </li>

              <li
                className={`py-2 px-4 lg:px-6 lg:py-2 bg-blue-300 hover:bg-slate-200 border border-slate-200 hover:text-blue-500 rounded-xl font-semibold ${
                  location.pathname === "/login"
                    ? "text-green-700 bg-slate-200 border border-slate-100"
                    : ""
                }`}
              >
                <Link to="/login">Login</Link>
              </li>
            </ul>
          </nav>
        </div>
      </header>
    </>
  );
};

export default Nav;
