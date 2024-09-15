import React, { FormEvent, useState } from "react";
import { useNavigate } from "react-router-dom";
import { LOGIN_PAGE } from "../routes/AppRouter";

import activateAccountAsync from "../../services/auth/activateAccount";

const EmailConfirmationPage = () => {
  const [confirmationCode, setConfirmationCode] = useState("");

  const navigate = useNavigate();

  const handleSubmit = async (event: FormEvent) => {
    event.preventDefault();

    const responseStatus = await activateAccountAsync(confirmationCode);
    if (responseStatus === 200) {
      alert("Account activated successfully.");
      navigate(LOGIN_PAGE);
    } else {
      alert("There is an error.");
    }
  };

  const handlePropertyChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setConfirmationCode(event.target.value);
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="flex items-center justify-center min-h-screen">
        <div className="text-xl flex-col text-base text-center pb-5 pt-5 bg-teal-100 rounded-lg space-y-2">
          <div className="px-3 text-base text-left">
            Confirm your email address to finish registration:
          </div>
          <div className="px-3">
            <input
              className="py-1 px-1"
              placeholder="Confirmation code"
              name="confirmationCode"
              value={confirmationCode || ""}
              onChange={handlePropertyChange}
            />
          </div>
          <button className="bg-teal-500 hover:bg-teal-700 text-white font-bold py-1 px-3 rounded">
            Register
          </button>
          <div className="text-xs">
            Back to login page?&nbsp;
            <a
              href={LOGIN_PAGE}
              rel="noopener noreferrer"
              className="text-blue-500 hover:text-blue-700"
            >
              Login
            </a>
          </div>
        </div>
      </div>
    </form>
  );
};

export default EmailConfirmationPage;
