import { useState } from "react";

const ZoomWrapper = ({ API_URL, selectedImage }) => {
  const [zoomLevel, setZoomLevel] = useState(1);
  const [dragging, setDragging] = useState(false);
  const [position, setPosition] = useState({ x: 0, y: 0 });
  const [dragStart, setDragStart] = useState({ x: 0, y: 0 });

  const handleZoom = (factor) => {
    setZoomLevel((prevZoom) => Math.max(0.1, prevZoom + factor));
  };

  const handleResetZoom = () => {
    setZoomLevel(1);
    setPosition({ x: 0, y: 0 });
  };

  const handleDragStart = (e) => {
    e.preventDefault();
    setDragging(true);
    setDragStart({ x: e.clientX, y: e.clientY });
  };

  const handleDragEnd = () => {
    setDragging(false);
  };

  const handleDrag = (e) => {
    if (dragging && zoomLevel > 1) {
      setPosition({
        x: position.x + (e.clientX - dragStart.x) / zoomLevel,
        y: position.y + (e.clientY - dragStart.y) / zoomLevel
      });
      setDragStart({ x: e.clientX, y: e.clientY });
    }
  };

  return (
    <>
      <div className="">
        {/* Zoom Buttons */}
        <div className="text-right">
          <button className="zoom-button" onClick={() => handleZoom(0.1)}>
            <span className="text-3xl px-2 border border-blue-300 hover:bg-blue-100">
              +
            </span>
          </button>
          <button className="zoom-button" onClick={() => handleZoom(-0.1)}>
            <span className="text-3xl ml-2 px-2 border border-blue-300">-</span>
          </button>
          <button className="zoom-button" onClick={handleResetZoom}>
            <span className="text-3xl ml-2 border border-blue-300 px-2">x</span>
          </button>
        </div>
        <div
          className="border border-gray-500"
          style={{
            position: "relative",
            width: "100%",
            height: "100%",
            overflow: "hidden"
          }}
        >
          <div
            style={{
              transform: `scale(${zoomLevel}) translate(${position.x}px, ${position.y}px)`,
              transformOrigin: "top left",
              cursor: zoomLevel > 1 ? (dragging ? "grabbing" : "grab") : "auto"
            }}
            onMouseDown={handleDragStart}
            onMouseUp={handleDragEnd}
            onMouseMove={handleDrag}
          >
            <div style={{ width: "100%", height: "100%" }}>
              <img
                src={`${API_URL}/datadir/uploads/${selectedImage}`}
                alt="Selected"
                className="mt-1 w-full"
                style={{ maxHeight: "100%" }}
              />
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default ZoomWrapper;
