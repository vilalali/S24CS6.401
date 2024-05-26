import React, { useState, useRef, useEffect, useCallback } from "react";
import {
  FaPlay,
  FaPause,
  FaStepForward,
  FaStepBackward,
  FaVolumeDown,
  FaVolumeUp
} from "react-icons/fa";

const Stage3AudioPlayer = ({ audioUrl }) => {
  const [isPlaying, setIsPlaying] = useState(false);
  const [volume, setVolume] = useState(0.5);
  const [currentTime, setCurrentTime] = useState(0);
  const [duration, setDuration] = useState(0);
  const audioRef = useRef(null);

  const togglePlay = () => {
    if (isPlaying) {
      audioRef.current.pause();
    } else {
      audioRef.current
        .play()
        .catch((error) => console.error("Error playing audio:", error));
    }
    setIsPlaying(!isPlaying);
  };

  const handleVolumeChange = (e) => {
    const newVolume = parseFloat(e.target.value);
    setVolume(newVolume);
    audioRef.current.volume = newVolume;
  };

  const handleTimeUpdate = useCallback(() => {
    setCurrentTime(audioRef.current.currentTime);
  }, []);

  const handleDurationChange = useCallback(() => {
    setDuration(audioRef.current.duration);
  }, []);

  const handleAudioEnd = useCallback(() => {
    setIsPlaying(false);
  }, []);

  useEffect(() => {
    const audioElement = audioRef.current;

    audioElement.volume = volume;
    audioElement.addEventListener("timeupdate", handleTimeUpdate);
    audioElement.addEventListener("loadedmetadata", handleDurationChange);
    audioElement.addEventListener("ended", handleAudioEnd);

    return () => {
      audioElement.removeEventListener("timeupdate", handleTimeUpdate);
      audioElement.removeEventListener("loadedmetadata", handleDurationChange);
      audioElement.removeEventListener("ended", handleAudioEnd);
    };
  }, [volume, handleTimeUpdate, handleDurationChange, handleAudioEnd]);

  const decreaseVolume = () => {
    const newVolume = Math.max(volume - 0.1, 0);
    setVolume(newVolume);
    audioRef.current.volume = newVolume;
  };

  const increaseVolume = () => {
    const newVolume = Math.min(volume + 0.1, 1);
    setVolume(newVolume);
    audioRef.current.volume = newVolume;
  };

  const formatTime = (time) => {
    const minutes = Math.floor(time / 60);
    const seconds = Math.floor(time % 60);
    return `${minutes}:${String(seconds).padStart(2, "0")}`;
  };

  const seekForward = () => {
    audioRef.current.currentTime += 10; // Seek forward 10 seconds
  };

  const seekBackward = () => {
    audioRef.current.currentTime -= 10; // Seek backward 10 seconds
  };

  const handleFormatTime = (e) => {
    const newTime = parseFloat(e.target.value) * duration;
    setCurrentTime(newTime);
    audioRef.current.currentTime = newTime;
  };

  return (
    <div className="bg-slate-300 text-slate-400 p-3 relative">
      <input
        type="range"
        className="w-full"
        value={currentTime / duration || 0}
        min="0"
        max="1"
        step="0.01"
        onChange={handleFormatTime}
      />
      <div className="text-end"></div>

      <audio ref={audioRef} src={audioUrl} />
      <div style={{ display: "flex", flexWrap: "wrap" }}>
        <div className="ml-[300px]" style={{ textAlign: "center", flex: "1" }}>
          <button className="mx-2" onClick={seekBackward}>
            <FaStepBackward />
          </button>
          <button className="mx-2" onClick={togglePlay}>
            {isPlaying ? <FaPause /> : <FaPlay />}
          </button>
          <button onClick={seekForward}>
            <FaStepForward />
          </button>
        </div>
        <div style={{ textAlign: "end", flex: "1" }}>
          <button onClick={decreaseVolume}>
            <FaVolumeDown />
          </button>
          <input
            type="range"
            style={{ width: "100px" }}
            value={volume}
            min="0"
            max="1"
            step="0.1"
            onChange={handleVolumeChange}
          />
          <button className="mr-2" onClick={increaseVolume}>
            <FaVolumeUp />
          </button>
          {formatTime(currentTime)} / {formatTime(duration)}
        </div>
      </div>
    </div>
  );
};

export default Stage3AudioPlayer;
