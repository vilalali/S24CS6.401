import React, { useState } from 'react';

const Register = () => {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    mobileNumber: '',
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

    // Name validation
    if (formData.firstName.trim() === '') {
      errors.firstName = 'This field is required';
    }

    if (formData.lastName.trim() === '') {
      errors.lastName = 'This field is required';
    }

    // Mobile number validation
    const mobileNumberRegex = /^[0-9]{10}$/;
    if (!mobileNumberRegex.test(formData.mobileNumber)) {
      errors.mobileNumber = 'Enter a valid 10-digit mobile number';
    }

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
        const response = await fetch('your_server_api_endpoint', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(formData),
        });

        if (response.ok) {
          console.log('Registration successful!');
          // Redirect or show success message
        } else {
          console.error('Registration failed.');
          // Handle error, e.g., display an error message to the user
        }
      } catch (error) {
        console.error('Error:', error);
        // Handle network error or other issues
      }
    }
  };

  return (
    <>
      <div className="flex justify-center mt-1 px-5">
        <div className="max-w-md bg-gray-200 p-5 rounded shadow-lg">
          <h1 className="text-2xl font-semibold mb-5">Register</h1>
          <form onSubmit={handleSubmit}>
            <input
              className={`w-full px-4 py-2 border rounded mb-2 focus:outline-none focus:ring focus:ring-blue-300 ${
                formErrors.firstName ? 'w-full border-red-500' : ''
              }`}
              type="text"
              placeholder="First Name"
              name="firstName"
              value={formData.firstName}
              onChange={handleInputChange}
            />
            {formErrors.firstName && (
              <p className="text-red-500 text-xs italic">{formErrors.firstName}</p>
            )}

            <input
              className={`w-full px-4 py-2 border rounded mb-2 focus:outline-none focus:ring focus:ring-blue-300 ${
                formErrors.lastName ? 'w-full border-red-500' : ''
              }`}
              type="text"
              placeholder="Last Name"
              name="lastName"
              value={formData.lastName}
              onChange={handleInputChange}
            />
            {formErrors.lastName && (
              <p className="text-red-500 text-xs italic">{formErrors.lastName}</p>
            )}

            <input
              className={`w-full px-4 py-2 border rounded mb-2 focus:outline-none focus:ring focus:ring-blue-300 ${
                formErrors.mobileNumber ? 'w-full border-red-500' : ''
              }`}
              type="text"
              placeholder="Mobile Number"
              name="mobileNumber"
              value={formData.mobileNumber}
              onChange={handleInputChange}
            />
            {formErrors.mobileNumber && (
              <p className="text-red-500 text-xs italic">{formErrors.mobileNumber}</p>
            )}

            <input
              className={`w-full px-4 py-2 border rounded mb-2 focus:outline-none focus:ring focus:ring-blue-300 ${
                formErrors.emailAddress ? 'w-full border-red-500' : ''
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
              className={`w-full px-4 py-2 border rounded mb-4 focus:outline-none focus:ring focus:ring-blue-300 ${
                formErrors.password ? 'w-full border-red-500' : ''
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
