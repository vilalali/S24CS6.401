import React, { useState } from 'react';
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Register = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    emailAddress: '',
    password: '',
  });

  const [formErrors, setFormErrors] = useState({});

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
    // Clear the error message when the user starts typing
    setFormErrors({
      ...formErrors,
      [name]: '',
    });
  };

  const validateForm = () => {
    const errors = {};
    // Email validation
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(formData.emailAddress)) {
      errors.emailAddress = 'Enter a valid email address';
    }

    // Password validation (modify as needed)
    if (formData.password.trim() === '') {
      errors.password = 'This field is required';
    } else {
      const passwordRegex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@#$%])[A-Za-z\d@#$%]{8,}$/;

      if (!passwordRegex.test(formData.password)) {
        errors.password = 'Password must contain at least 8 characters with one uppercase letter, one lowercase letter, one digit, and one of the following special characters: @#$%';
      }
    }


    setFormErrors(errors);
    return Object.keys(errors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (validateForm()) {
      try {
        const response = await axios.post("http://192.168.0.99:8000/api/login", {
          username: formData.emailAddress,
          password: formData.password,
        });

        const result = await response.data;
        console.log(result);

        if (result.auth) {
          localStorage.setItem("user", result.username);
          localStorage.setItem("token", result.auth);
          navigate("/home");
        } else {
          console.error('Registration failed.');
          // Handle error, e.g., display an error message to the user
        }
      } catch (error) {
        console.log("Error:", error.response.data.message);
        // Handle network error or other issues
      }
    }
  };

  return (
    <>
      <div className="flex justify-center mt-1 px-5">
        <div className="max-w-md bg-gray-200 p-5 rounded shadow-lg">
          <h1 className="text-2xl font-semibold mb-5">Login</h1>
          <form onSubmit={handleSubmit}>
            <input
              className={`w-full px-4 py-2 border rounded mb-2 focus:outline-none focus:ring focus:ring-blue-300 ${formErrors.emailAddress ? 'w-full border-red-500' : ''
                }`}
              type="text"
              placeholder="Email Address"
              name="emailAddress"
              value={formData.emailAddress}
              onChange={handleInputChange}
            />
            {formErrors.emailAddress && (
              <p className="text-red-500 text-xs italic">{formErrors.emailAddress}</p>
            )}

            <input
              className={`w-full px-4 py-2 border rounded mb-4 focus:outline-none focus:ring focus:ring-blue-300 ${formErrors.password ? 'w-full border-red-500' : ''
                }`}
              type="password"
              placeholder="Password"
              name="password"
              value={formData.password}
              onChange={handleInputChange}
            />
            {formErrors.password && (
              <p className="text-red-500 text-xs italic">{formErrors.password}</p>
            )}

            <div className="text-center">
              <button
                className="w-24 bg-blue-600 hover:bg-blue-700 text-white py-2 rounded text-sm tracking-wide"
                type="submit"
              >
                Register
              </button>
            </div>
          </form>
        </div>
      </div>
    </>
  );
};

export default Register;
