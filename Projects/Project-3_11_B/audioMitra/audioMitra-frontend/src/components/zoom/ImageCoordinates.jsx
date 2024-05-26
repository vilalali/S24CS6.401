import React, { useEffect, useRef, useState } from 'react';

const ImageCoordinates = () => {
  const imgRef = useRef(null);
  const [coordinates, setCoordinates] = useState({ x: 0, y: 0 });
  const [cordinateList, setCordinateList] = useState([]);

  useEffect(() => {
    const getCoordinates = (e) => {
      if (imgRef.current) {
        const imgBounds = imgRef.current.getBoundingClientRect();
        const scaleFactorX = imgRef.current.naturalWidth / imgBounds.width;
        const scaleFactorY = imgRef.current.naturalHeight / imgBounds.height;

        const posX = (e.clientX - imgBounds.left) * scaleFactorX;
        const posY = (e.clientY - imgBounds.top) * scaleFactorY;

        setCordinateList((prevList) => [
          ...prevList,
          { x: posX.toFixed(2), y: posY.toFixed(2) },
        ]);

        setCoordinates({ x: posX.toFixed(2), y: posY.toFixed(2) });
      }
    };

    const imgElement = imgRef.current;
    imgElement.addEventListener('mousedown', getCoordinates);

    return () => {
      imgElement.removeEventListener('mousedown', getCoordinates);
    };
  }, []);

  console.log(cordinateList);

  const handleCropImages = () => {
    // Assuming your Flask server is running locally on port 5000
    const apiUrl = 'http://localhost:5000/crop-images';

    // Make API call to Flask server
    fetch(apiUrl, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ coordinates: cordinateList }),
    })
      .then(response => response.json())
      .then(data => console.log(data))
      .catch(error => console.error('Error:', error));
  };

  return (
    <div>
      <img
        src="page_4.jpg"
        className='border border-gray-600'
        width="500"
        height="600"
        alt=""
        id="myImgId"
        ref={imgRef}
      />
      {JSON.stringify(cordinateList)}
      <p>X: <span>{coordinates.x}</span></p>
      <p>Y: <span>{coordinates.y}</span></p>
      <button className='text-3xl bg-blue-400' onClick={handleCropImages}> Submit </button>
    </div>
  );
};

export default ImageCoordinates;
