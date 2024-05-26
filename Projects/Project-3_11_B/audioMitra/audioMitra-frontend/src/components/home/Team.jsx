import React from "react";
import TeamData from "./TeamDetails.json";

const Team = () => {
  return (
    <div className=" mx-auto mt-8 px-4 bg-gray-100 pb-10  mb-10">
      <h1 className="text-4xl  sm:text-3xl text-blue-600  font-sans text-center font-bold p-10">
        Meet Our Team
      </h1>
      <div className="grid grid-cols-1 md:grid-cols-4 gap-4 mx-8 ">
        {TeamData.map((member) => (
          <div
            key={member.id}
            className="bg-white shadow-lg text-black font-sans mb-4 border-2 border-gray-300 rounded-md overflow-hidden hover:bg-gray-600 hover:text-white transition ease-in-out duration-300"
          >
            <div className="flex">
              {/* <img
                src={member.profileImage}
                alt={member.fullName}
                className="w-36 h-36 object-cover p-4 rounded-full "
              /> */}
              <div className="p-4">
                <h3 className="text-xl  font-sans font-semibold">
                  {member.fullName}
                </h3>
                <p className="text-sm text-black font-sans  hover:text-white">
                  {member.designation}
                </p>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Team;
