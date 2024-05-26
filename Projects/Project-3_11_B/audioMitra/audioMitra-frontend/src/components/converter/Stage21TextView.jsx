import React, { useRef, useEffect } from "react";

const Stage21TextView = ({ setEditedText, editedText }) => {
  const textareaRef = useRef(null);

  useEffect(() => {
    if (textareaRef.current) {
      textareaRef.current.style.height = "auto";
      textareaRef.current.style.height = `${textareaRef.current.scrollHeight}px`;
    }
  }, [editedText]);

  return (
    <textarea
      ref={textareaRef}
      className="text-justify border h-[400px] border-gray-400 rounded p-3 wrap-text mx-4 focus:outline-none focus:ring focus:ring-blue-300 focus:px-1 bg-gray-100"
      style={{
        width: "100%",
        maxHeight: "100vh",
        overflowY: "auto",
        resize: "none"
      }}
      value={editedText}
      onChange={(e) => setEditedText(e.target.value)}
    />
  );
};

export default Stage21TextView;
