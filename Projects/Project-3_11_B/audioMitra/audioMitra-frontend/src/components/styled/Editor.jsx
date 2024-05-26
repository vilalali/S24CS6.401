import React, { useState, useRef, useEffect } from 'react';

const TextareaWithFindReplace = ({ setTextareaContent, textareaContent }) => {
  const [findText, setFindText] = useState('');
  const [replaceText, setReplaceText] = useState('');
  const textareaRef = useRef(null);

  useEffect(() => {
    if (textareaRef.current) {
      // Check if the content is overflowing
      const isOverflowing = textareaRef.current.scrollHeight > textareaRef.current.clientHeight;
      console.log('Content overflowing:', isOverflowing);
    }
  }, [textareaContent]);

  const handleTextareaChange = (event) => {
    setTextareaContent(event.target.value);
  };



  return (
    <div>
      <textarea
        ref={textareaRef}
        value={textareaContent}
        onChange={handleTextareaChange}
        className="w-full h-32 border p-2"
        readOnly
      />
    </div>
  );
};

export default TextareaWithFindReplace;
