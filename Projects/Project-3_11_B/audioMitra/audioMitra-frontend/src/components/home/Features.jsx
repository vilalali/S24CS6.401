import React from "react";
import { Link } from "react-router-dom";
import Picture2Text from "./homeImages/Picture2Text.jpg"
import Text2Speech from "./homeImages/Text2Speech.jpg"
import Picture2Text2Speech from "./homeImages/Picture2Text2Speech.jpg"

const Features = () => {
  return (
    <div className="bg-white pt-2 ">
      <div className="mx-auto">
        <div>
          <h2 className="text-blue-600 text-center font-sans font-bold pt-10 pb-12 text-3xl">
            Features
          </h2>
          <div className="flex flex-wrap justify-center px-6 mt-1">
            <div className="p-2">
              <div className="rounded-xl flex flex-wrap justify-center mb-12">
                <div className="w-full sm:w-1/2 md:w-1/3 p-4 hover:scale-105 transition-transform duration-300 ease-in-out">
                  <Link
                    to="/converter"
                    className="hover:no-underline"
                  >
                    <div className="rounded-xl border-1 border-gray-2600 shadow-md">
                      <img
                        src={Picture2Text}
                        alt="OCR"
                        className="object-cover h-60 w-full rounded-t-lg"
                      />
                      <p className="text-center font-sans font-semibold text-lg py-4">
                        Image To Text
                      </p>
                    </div>
                  </Link>
                </div>

                <div className="w-full sm:w-1/2 md:w-1/3 p-4 hover:scale-105 transition-transform duration-300 ease-in-out">
                  <Link
                    to="/converter"
                    className="hover:no-underline"
                  >
                    <div className="rounded-xl border-1 border-gray-2600 shadow-md">
                      <img
                        src={Text2Speech}
                        alt="Text To Speech"
                        className="object-cover h-60 w-full rounded-t-lg"
                      />
                      <p className="text-center font-sans font-semibold text-lg py-4">
                        Text To Speech
                      </p>
                    </div>
                  </Link>
                </div>

                <div className="w-full sm:w-1/2 md:w-1/3 p-4 hover:scale-105 transition-transform duration-300 ease-in-out">
                  <Link
                    to="/converter"
                    className="hover:no-underline"
                  >
                    <div className="rounded-xl border-1 border-gray-2600 shadow-md">
                      <img
                        src={Picture2Text2Speech}
                        alt="To Text To Speech"
                        className="object-cover h-60 w-full rounded-t-lg"
                      />
                      <p className="text-center font-sans font-semibold text-lg py-4">
                        Image To Text To Speech
                      </p>
                    </div>
                  </Link>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Features;
