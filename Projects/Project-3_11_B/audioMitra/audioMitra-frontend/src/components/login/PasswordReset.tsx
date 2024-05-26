import axios from "axios";
import React, { useState, useContext } from "react";
import { url } from "../../creds.js";
import { ToastContext } from "../../App";
import { TOAST_VARIANTS } from "../../packages/toasts/constants";
import {Button} from "../../components/styled/Buttons";

const form_class =
  "px-4 py-4 mx-4 my-4 border border-solid border-current rounded-lg bg-white shadow-sign-in";

export default function PasswordReset() {
  const [email, setEmail] = useState<string>("");
  const [loading, setLoading] = useState<boolean>(false);
  const [emailSent, setEmailSent] = useState<boolean>(false);
  const { addToast } = useContext(ToastContext);

  const submit = (e: any): void => {
    e.preventDefault();
    setLoading(true);
    console.log(email);
    if(loading) return;
    if (!email) {
      // AMEY-TODO : email validation
      addToast({
        message: "Please enter valid email",
        variant: TOAST_VARIANTS.ERROR,
      });

      return;
    }

    axios
      .post(url + "get-password-reset-link?email=" + email)
      .then((res) => {
        if (res.data.error) {
          addToast({
            message: "User does not exist",
            variant: TOAST_VARIANTS.ERROR,
          });
        } else {
          setEmailSent(true);
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

  if (emailSent)
    return (
      <div className={form_class}>
        <div className="text-center">
          <p>Link to reset password has be sent on registered email id.</p>
        </div>
      </div>
    );

  return (
    <div className="bg-white py-10 px-6 m-6 sm:rounded-lg sm:px-10">
      <form className={`form_class space-y-6`} onSubmit={submit}>
        <div className="mx-auto ">
          <label className="text-base mb-4" htmlFor="form2Example1">
            Registered Email Address
          </label>
          <input
            type="email"
            id="form2Example1"
            value={email}
            className="appearance-none rounded-md relative block w-full mt-2 px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>
        <Button.Blue
          type="submit"
          className="bg-blue-500 text-base text-white rounded w-full mb-4 cursor-pointer hover:bg-blue-700"
        >
          {loading ? "Please wait" : "Reset Password"}
        </Button.Blue>

        <div className="text-base text-center">
          <p>
            <strong>
              Link to reset password will be sent on registered email id.
            </strong>
          </p>
          <p>
            If you no longer have access to the registered email id <br />
            please contact support.
          </p>
        </div>
      </form>
    </div>
  );
}
