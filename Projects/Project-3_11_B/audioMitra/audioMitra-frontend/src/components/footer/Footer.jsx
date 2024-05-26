import React from "react";

const Footer = () => {
  return (
    <div>
      <footer className="w-full text-3xl text-gray-600 bg-gray-100 border-t border-gray-300 py-4 text-center">
        <small>
          Audio Mitra Designed By Team_11 {new Date().getFullYear()} | All
          Rights Reserved
        </small>
      </footer>
    </div>
  );
};

export default Footer;
