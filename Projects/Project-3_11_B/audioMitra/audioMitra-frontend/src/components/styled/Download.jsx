import { useState, useRef, useEffect } from 'react';
import { Link } from 'react-router-dom';

const Download = ({ fileName, fileUrl, downloadDocxFile }) => {
  const [isDropdownOpen, setDropdownOpen] = useState(false);
  const dropdownRef = useRef(null);

  useEffect(() => {
    const handleOutsideClick = (event) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
        setDropdownOpen(false);
      }
    };

    document.addEventListener('mousedown', handleOutsideClick);

    return () => {
      document.removeEventListener('mousedown', handleOutsideClick);
    };
  }, []);

  const openDropdown = () => {
    setDropdownOpen(true);
  };

  const closeDropdown = () => {
    setDropdownOpen(false);
  };

  const handleTextClick = () => {
    // Add logic for handling the "Text" click if needed
    closeDropdown();
  };

  const handleDocsClick = () => {
    // Add logic for handling the "Docs" click if needed
    downloadDocxFile();
    closeDropdown();
  };

  return (
    <>
      <div className="relative inline-block text-left" ref={dropdownRef}>
        <div>
          <button
            type="button"
            className="inline-flex w-full justify-center gap-x-1.5 rounded-md bg-white px-3 py-3 text-sm font-semibold text-gray-900 shadow-md ring-1 ring-inset ring-gray-300 hover:bg-blue-100"
            id="menu-button"
            aria-expanded={isDropdownOpen}
            aria-haspopup="true"
            onClick={openDropdown}
          >
            Download
            <svg
              className="-mr-1 h-5 w-5 text-gray-400"
              viewBox="0 0 20 20"
              fill="currentColor"
              aria-hidden="true"
            >
              <path
                fillRule="evenodd"
                d="M5.23 7.21a.75.75 0 011.06.02L10 11.168l3.71-3.938a.75.75 0 111.08 1.04l-4.25 4.5a.75.75 0 01-1.08 0l-4.25-4.5a.75.75 0 01.02-1.06z"
                clipRule="evenodd"
              />
            </svg>
          </button>
        </div>

        {isDropdownOpen && (
          <div
            className="absolute right-0 z-10 mt-2 w-56 origin-top-right rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none"
            role="menu"
            aria-orientation="vertical"
            aria-labelledby="menu-button"
            tabIndex="-1"
          >
            <div className="py-1" role="none">
              <Link
                download={fileName}
                to={fileUrl}
                target="_blank"
                rel="noreferrer"
                className="text-gray-700 block px-4 py-2 text-sm hover:bg-blue-100"
                role="menuitem"
                tabIndex="-1"
                id="menu-item-0"
                onClick={handleTextClick}
              >
                Text File
              </Link>
              <Link
                to="#"
                className="text-gray-700 block px-4 py-2 text-sm hover:bg-blue-100"
                role="menuitem"
                tabIndex="-1"
                id="menu-item-1"
                onClick={handleDocsClick}
              >
                Docs File
              </Link>
            </div>
          </div>
        )}
      </div>
    </>
  );
};

export default Download;
