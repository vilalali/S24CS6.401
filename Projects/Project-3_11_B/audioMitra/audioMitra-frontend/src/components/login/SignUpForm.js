import axios from "axios";
import React, { useState, useMemo, useContext } from "react";
import { useNavigate } from "react-router-dom";
import creds from "../../creds";
import { ToastContext } from "../../App";
import { TOAST_VARIANTS } from "../../packages/toasts/constants";
import { Button } from "../../components/styled/Buttons";
import { FaEye, FaEyeSlash } from "react-icons/fa";

const backendUrl = creds.backendUrl;
const NAME_REGEX = RegExp(/^[A-Za-z]+$/);
const EMAIL_REGEX = RegExp(
  /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i
);
const InvalidFormStyle =
  "bg-red-100 border border-red-500 text-red-900 placeholder-red-700 text-xs rounded-md focus:ring-red-500 focus:border-red-500 block w-full p-2.5 dark:bg-red-100 dark:border-red-400";


const SignUpInputField = ({
  fieldName,
  labelName,
  inpType,
  touched,
  setTouched,
  errors,
  inputField,
  setInputField
}) => {
  return (
    <div className="mb-4">
      <label for={fieldName} className="text-base">
        {labelName}
      </label>
      <input
        type={inpType}
        id={fieldName}
        className={
          touched && errors
            ? InvalidFormStyle
            : "appearance-none rounded-md block w-full border border-gray-300 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
        }
        value={inputField}
        onBlur={() => setTouched(true)}
        onChange={(e) => setInputField(e.target.value)}
      />
      {errors ? (
        <p className="  mt-2 text-xs text-red-600 dark:text-red-500">
          {errors}
        </p>
      ) : (
        <p className="  mt-2 text-xs text-red-600 dark:text-red-500">
          {errors}
        </p>
      )}
    </div>
  );
};

const validate = (
  emailTouched,
  emailInputField,
  passwordTouched,
  passwordInputField,
  firstNameInputField,
  firstNameTouched,
  lastNameTouched,
  lastNameInputField,
  institutionTouched,
  institutionInputField,
  designationTouched,
  designationInputField
) => {
  const errors = {
    email:
      emailTouched && !EMAIL_REGEX.test(emailInputField)
        ? "Invalid Email id"
        : "",
    password:
      passwordTouched && passwordInputField.length < 6
        ? "Password should be atleast 6 characters long"
        : "",
    firstname:
      firstNameTouched && firstNameInputField.length < 1
        ? "First name is required field"
        : firstNameTouched && !NAME_REGEX.test(firstNameInputField)
          ? "First name can only contain alphabets"
          : "",
    lastname:
      lastNameTouched && lastNameInputField.length < 1
        ? "Last name is required field"
        : lastNameTouched && !NAME_REGEX.test(lastNameInputField)
          ? "Last name can only contain alphabets"
          : "",
    institution:
      institutionTouched && institutionInputField.length < 1
        ? "Institution is required field"
        : "",
    designation:
      designationTouched && designationInputField.length < 1
        ? "Designation is required field"
        : "",
  };
  return errors;
};

const checkForNullErrors = (errors) => {
  let error_message = "",
    cnt = 0;
  for (let key in errors) {
    if (errors[key]) {
      cnt += 1;
      cnt < 2 ? (error_message += " " + key) : (error_message += ", " + key);
    }
  }
  return error_message;
};

export default function SignUpForm() {
  const navigate = useNavigate();
  const [showPassword, setShowPassword] = useState(false);
  const passwordInputType = showPassword ? "text" : "password";

  const [firstNameInputField, setFirstNameInputField] = useState("");
  const [lastNameInputField, setLastNameInputField] = useState("");
  const [passwordInputField, setPasswordInputField] = useState("");
  const [emailInputField, setEmailInputField] = useState("");
  const [institutionInputField, setInstitutionInputField] = useState("");
  const [designationInputField, setDesignationInputField] = useState("");
  const [roleInputField, setRoleInputField] = useState("user");

  const [firstNameTouched, setFirstNameTouched] = useState(false);
  const [lastNameTouched, setLastNameTouched] = useState(false);
  const [passwordTouched, setPasswordTouched] = useState(false);
  const [emailTouched, setEmailTouched] = useState(false);
  const [institutionTouched, setInstitutionTouched] = useState(false);
  const [designationTouched, setDesignationTouched] = useState(false);

  const { addToast } = useContext(ToastContext);

  const errors = useMemo(
    () =>
      validate(
        emailTouched,
        emailInputField,
        passwordTouched,
        passwordInputField,
        firstNameInputField,
        firstNameTouched,
        lastNameTouched,
        lastNameInputField,
        institutionTouched,
        institutionInputField,
        designationTouched,
        designationInputField
      ),
    [
      emailTouched,
      emailInputField,
      passwordTouched,
      passwordInputField,
      firstNameInputField,
      firstNameTouched,
      lastNameTouched,
      lastNameInputField,
      institutionTouched,
      institutionInputField,
      designationTouched,
      designationInputField
    ]
  );

  const handleSubmit = (e) => {
    e.preventDefault();
    const error_message = checkForNullErrors(errors);
    if (error_message) {
      addToast({
        message: "Fill" + error_message + " correctly",
        variant: TOAST_VARIANTS.WARNING
      });
    } else {
      const formFields = {
        firstname: firstNameInputField,
        lastname: lastNameInputField,
        password: passwordInputField,
        email: emailInputField,
        institution: institutionInputField,
        designation: designationInputField,
        role: roleInputField
      };
      axios
        .post(backendUrl + "register", formFields)
        .then((res) => {
          addToast({
            message: "User Created Successfully, proceeding to sign in",
            variant: TOAST_VARIANTS.SUCCESS
          });
          return navigate("/sign-in");
        })
        .catch((err) => {
          addToast({
            message: err.message + "\n" + err.response.data.message,
            variant: TOAST_VARIANTS.ERROR
          });
        });
    }
  };
  return (
    <div className="wx-auto sm:w-2/5">
      <div className="w-full sm:w-full text-white rounded text-center bg-blue-green-0 mt-8 mb-4 py-2">
        New Registration
      </div>
      <div className="bg-white shadow-md rounded px-4 pt-4 pb-1 mb-8">
        <form className="space-y-6 text-xs sm:text-sm " onSubmit={handleSubmit}>
          <div className="w-full sm:flex ">
            <div className="px-3 w-full sm:w-1/2 sm:mt-0">
              <SignUpInputField
                fieldName="firstname"
                labelName="First Name"
                inpType="text"
                touched={firstNameTouched}
                setTouched={setFirstNameTouched}
                errors={errors.firstname}
                inputField={firstNameInputField}
                setInputField={setFirstNameInputField}
              />
            </div>
            <div className="px-3 w-full sm:w-1/2 sm:mt-0">
              <SignUpInputField
                fieldName="lastname"
                labelName="Last Name"
                inpType="text"
                touched={lastNameTouched}
                setTouched={setLastNameTouched}
                errors={errors.lastname}
                inputField={lastNameInputField}
                setInputField={setLastNameInputField}
              />
            </div>
          </div>
          <div className="w-full sm:flex ">
            <div className="px-3 w-full sm:w-1/2 sm:mt-0">
              <SignUpInputField
                fieldName="email"
                labelName="Email"
                inpType="email"
                touched={emailTouched}
                setTouched={setEmailTouched}
                errors={errors.email}
                inputField={emailInputField}
                setInputField={setEmailInputField}
              />
            </div>
            <div className="px-3 w-full sm:w-1/2 sm:mt-0">
              <div className="relative">
                <SignUpInputField
                  fieldName="password"
                  labelName="Password"
                  inpType={passwordInputType}
                  touched={passwordTouched}
                  setTouched={setPasswordTouched}
                  errors={errors.password}
                  inputField={passwordInputField}
                  setInputField={setPasswordInputField}
                />
                
                <button
                  type="button"
                  onClick={() => setShowPassword(!showPassword)}
                  className="text-gray-60 absolute inset-y-0 right-0 mt-8 flex  text-sm leading-5"
                >
                  {showPassword ? <FaEyeSlash className="h-5 w-5 text-gray-500 mr-2"/> : <FaEye className="h-5 w-5 text-gray-500 mr-2"/>}
                </button>
              </div>
            </div>
          </div>
          <div className="w-full sm:flex">
            <div className="px-3 w-full sm:w-1/2 sm:mt-0">
              <SignUpInputField
                fieldName="institution"
                labelName="Institution"
                inpType="text"
                touched={institutionTouched}
                setTouched={setInstitutionTouched}
                errors={errors.institution}
                inputField={institutionInputField}
                setInputField={setInstitutionInputField}
              />
            </div>
            <div className="px-3 w-full sm:w-1/2 sm:mt-0">
              <SignUpInputField
                fieldName="designation"
                labelName="Designation"
                inpType="text"
                touched={designationTouched}
                setTouched={setDesignationTouched}
                errors={errors.designation}
                inputField={designationInputField}
                setInputField={setDesignationInputField}
              />
            </div>
          </div>
          <div className="w-full sm:flex">
            <div className="px-3 w-full text-base sm:w-1/2">
              <label htmlFor="role">Role</label>
              <select
                id="role"
                name="role"
                value={roleInputField}
                className="appearance-none relative block px-3 py-2 border-gray-300 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm p-2 w-full border rounded-md overflow-auto"
                onChange={(e) => setRoleInputField(e.target.value)}
              >
                <option value="user">User</option>
                <option value="admin" disabled>
                  Admin
                </option>
                <option value="moderator">Moderator</option>
              </select>
            </div>
            <div className="w-full  px-3 mt-5 sm:w-1/2">
              <Button.Blue
                type="submit"
                className="w-full btn btn-primary btn-block mt-1"
              >
                Sign Up
              </Button.Blue>
            </div>
          </div>
          <div className="text-center ">
            <p className="text-sm font-light text-gray-500 dark:text-gray-400 my-4">
              Already a member?
              <a
                className="font-medium text-blue-600 hover:underline dark:text-blue-500"
                href="/sign-in"
              >
                Login
              </a>
            </p>
          </div>
        </form>
      </div>
    </div>
  );
}