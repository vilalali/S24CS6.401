import axios from "axios";
import React, { useState, useContext } from "react";
import { Navigate } from "react-router-dom";
import { url } from "../../creds.js";
import { useParams } from "react-router-dom";
import { ToastContext } from "../../App";
import { TOAST_VARIANTS } from "../../packages/toasts/constants";
import { Button } from "../../components/styled/Buttons"; 

const form_class ="px-10 py-4 my-4 justify-center items-center rounded-lg bg-white shadow-md sm:w-1/4";
 
export default function ResetPassword() {
  const { token } = useParams();
 
  const [password, setPassword] = useState<string>("");
  const [cpassword, setCPassword] = useState<string>("");
  const [loading, setLoading] = useState<boolean>(false);
  const [passwordReset, setPasswordReset] = useState<boolean>(false);
  const { addToast } = useContext(ToastContext);
 
  const submit = (e: any): void => {
    e.preventDefault();
    setLoading(true);
    console.log(password);
    if(loading) return;
    if (password.length < 6) {
      // AMEY-TODO : password validation
      addToast({
        message: "Please enter valid password",
        variant: TOAST_VARIANTS.ERROR,
      });
      return;
    }
    if(password !== cpassword){
      addToast({
        message: "Passwords do not match",
        variant: TOAST_VARIANTS.ERROR,
      });
      return;
    }
 
    axios
      .post(url + "password-reset", {
        password, token
      })
      .then((res) => {
        if (res.data.error) {
          addToast({
            message: "Link has expired or is incorrect",
            variant: TOAST_VARIANTS.ERROR,
          });
        } else {
          setPasswordReset(true);
        }
      })
      .catch((err) => {
        if (err.message) {
          addToast({
            message: "SERVER ERROR",
            variant: TOAST_VARIANTS.WARNING,
          });
        } else {
          addToast({
            message: "UNEXPECTED ERROR, PROBABLY SERVER UNAVAILABLE",
            variant: TOAST_VARIANTS.ERROR,
          });
        }
      })
      .finally(() => setLoading(false));
  };
 
  if (passwordReset)
    return (
      <div className={form_class}>
        <div className="text-center">
          <p>Password has been successfully reset.</p>
          <p>Please sign in.</p>
        </div>
      </div>
    );
 
  return (
    <div className={form_class}>
      <form onSubmit={submit}>
        <div className="py-2 mb-4 space-y-2 sm:rounded-lg ">
          <label className="text-base " htmlFor="pass">
            Enter New Password
          </label>
          <input
            type="password"
            id="pass"
            value={password}
            className={
              password === ""
                ? "appearance-none rounded-md relative block w-full px-3 border border-gray-300 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                : "appearance-none rounded-md relative block w-full px-3 border border-gray-300 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm bg-gray-200"
            }
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        <div className="py-2 mb-4 space-y-2 sm:rounded-lg ">
          <label className="text-base" htmlFor="conf">
            Confirm New Password
          </label>
          <input
            type="password"
            id="conf"
            value={cpassword}
            className={
              password === ""
                ? "appearance-none rounded-md relative block w-full px-3 border border-gray-300 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                : "appearance-none rounded-md relative block w-full px-3 border border-gray-300 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm bg-gray-200"
            }
            onChange={(e) => setCPassword(e.target.value)}
          />
        </div>
 
        <Button.Blue type="submit" className="w-full my-2 cursor-pointer">
          {loading ? "Please wait" : "Reset Password"}
        </Button.Blue>
      </form>
    </div>
  );
}
 