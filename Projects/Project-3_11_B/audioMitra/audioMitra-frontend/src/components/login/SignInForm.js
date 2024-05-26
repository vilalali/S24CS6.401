import axios from "axios";
import React, { useState, useContext } from "react";
import { Navigate, useSearchParams } from "react-router-dom";
import { url } from "../../creds.js";
import { Button } from "../../components/styled/Buttons";
import { ToastContext } from "../../App";
import { TOAST_VARIANTS } from "../../packages/toasts/constants";
import { FaEye, FaEyeSlash } from "react-icons/fa";

export default function SignInForm() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [searchParams] = useSearchParams();
  const redirect = searchParams.get("redirect");
  const [showPassword, setShowPassword] = useState(false);
  const { addToast } = useContext(ToastContext);

  const onSubmit = (e) => {
    e.preventDefault();
    console.log(email, password);
    axios
      .post(url + "login", { email, password })
      .then((res) => {
        console.log(res.data);
        addToast({
          message: "Login Successful",
          variant: TOAST_VARIANTS.SUCCESS
        });
        localStorage.setItem("dfs-user", JSON.stringify(res.data.data));
        if (redirect) {
          window.location = redirect;
        }
        setEmail("");
        setPassword("");
      })
      .catch((err) => {
        console.log("AMEY", err.response?.data?.data);
        if (err.response?.data?.data === "USERNAME_PASSWORD_MISSMATCH") {
          addToast({
            message: "Username and password do not match",
            variant: TOAST_VARIANTS.ERROR
          });
        } else {
          addToast({
            message: "Server error, please contact support",
            variant: TOAST_VARIANTS.ERROR
          });
        }
      });
  };

  if (localStorage.getItem("dfs-user")) {
    if (redirect) {
      window.location = redirect;
    } else return <Navigate to="/my-data" replace={true} />;
  }

  return (
    <div className="max-w-3xl w-full rounded-l my-8">
      <div className="bg-gray-100 flex flex-col justify-center px-6 sm:px-8 lg:px-10">
        <div className="mx-auto w-full sm:max-w-lg">
          <div className="sm:w-full text-white rounded text-center bg-blue-green-0 mt-1 mb-4 py-2">
            Sign In
          </div>
          <div className="bg-white py-10 px-6 sm:rounded-lg sm:px-10">
            <form onSubmit={onSubmit}>
              <div className="space-y-6">
                <div className="space-y-2">
                  <label htmlFor="email" className="text-base">
                    Email address
                  </label>
                  <input
                    onChange={(e) => setEmail(e.target.value)}
                    id="form2Example1"
                    name="email"
                    type="email"
                    placeholder="Email"
                    autoComplete="email"
                    required
                    className="appearance-none rounded-md relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                  />
                </div>
                <div className="space-y-2">
                  <label htmlFor="password" className=" text-base">
                    Password
                  </label>
                  <div className="relative">
                    <input
                      value={password}
                      type={showPassword ? "text" : "password"}
                      name="password"
                      id="form2Example2"
                      placeholder="Password"
                      className="text-md rounded-md block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                      onChange={(e) => setPassword(e.target.value)}
                      required
                    />
                    <button
                      type="button"
                      onClick={() => setShowPassword(!showPassword)}
                      className="absolute inset-y-0 right-0 pr-3 flex items-center text-sm leading-5"
                    >
                      {showPassword ? <FaEyeSlash className="h-5 w-5 text-gray-500"/> : <FaEye className="h-5 w-5 text-gray-500"/>}
                    </button>
                  </div>
                </div>
              </div>
              <div className="flex items-center justify-between mt-4 mb-4 space-y-6">
                <div>
                  <input
                    type="checkbox"
                    value=""
                    id="form2Example31"
                    name="rememberMe"
                    className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
                  />
                  <label
                    htmlFor="remember_me"
                    className="ml-2  text-sm text-gray-900"
                  >
                    Remember me
                  </label>
                </div>
                <div className="text-sm">
                  <a
                    href="/password-reset"
                    className="font-semibold text-indigo-600 no-underline hover:text-indigo-500 hover:underline"
                  >
                    Reset password
                  </a>
                </div>
              </div>
              <Button.Blue type="submit" className="flex w-full justify-center">
                Sign in
              </Button.Blue>
              <div className="text-center m-2">
                <p>
                  Not a member?
                  <a
                    href="/sign-up"
                    className="font-semibold text-indigo-600 no-underline hover:text-indigo-500 hover:underline"
                  >
                    Register
                  </a>
                </p>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}