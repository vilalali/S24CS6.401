import React from "react";
import BannerTop from "./homeImages/Banner.jpg"

export const Banner = () => {
  return (
    <>
      <div className="relative py-24 px-4">
        <div className="mx-auto h-full w-full">
          <div className="z-20 relative text-black mx-auto justify-center items-center text-center">
            <h1 className="text-4xl font-sans sm:text-5xl md:text-6xl lg:text-7xl font-bold">
              Audio Mitra Powered by SE Team_11
            </h1>

            <h2 className="text-xl  font-sans sm:text-2xl md:text-3xl lg:text-4xl p-4 font-bold">
              Pioneering Innovation through
            </h2>
            <h2 className="text-xl  font-sans sm:text-2xl md:text-3xl lg:text-4xl p-3 font-bold">
              Unified Solutions
            </h2>
          </div>
          <div className="absolute inset-0 h-auto z-10">
            <img
              src={BannerTop}
              alt="Audio Mitra Banner"
              className="h-full w-full object-cover"
            />
          </div>
        </div>
      </div>
    </>
  );
};

export default Banner;
