import React, { FormEvent, useState } from "react";
import RegisterInput from "../../models/authentication/RegisterInput";
import { useNavigate } from "react-router-dom";
import { useLocation } from "react-router-dom";
import { LOGIN_PAGE } from "../routes/AppRouter";
import { EMAIL_CONFIRMATION_PAGE } from "../routes/AppRouter";

import registerUserAsync from "../../services/auth/registerUser";

const RegisterPage = () => {
  const [registerInput, setRegisterInput] = useState<RegisterInput>({
    currency: "RSD",
  } as RegisterInput);

  const navigate = useNavigate();

  const handleSubmit = async (event: FormEvent) => {
    event.preventDefault();
    const responsestatus = await registerUserAsync(
      registerInput.username,
      registerInput.password,
      registerInput.confirmPassword,
      registerInput.firstName,
      registerInput.lastName,
      registerInput.email,
      registerInput.balance,
      registerInput.currency
    );
    if (responsestatus === 202) {
      navigate(EMAIL_CONFIRMATION_PAGE);
    }
  };

  const handlePropertyChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setRegisterInput((prevUserInput) => {
      return {
        ...prevUserInput,
        [event.target.name]: event.target.value,
      } as RegisterInput;
    });
  };

  const handleSelectChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setRegisterInput((prevUserInput) => {
      return {
        ...prevUserInput,
        [event.target.name]: event.target.value,
      } as RegisterInput;
    });
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="flex items-center justify-center min-h-screen">
        <div className="text-xl flex-col text-base text-center pb-5 pt-5 bg-teal-100 rounded-lg space-y-2">
          <div className="px-3 text-base text-left">Register new account:</div>
          <div className="px-5">
            <input
              className="py-1 px-1"
              placeholder="Username"
              name="username"
              value={registerInput.username || ""}
              onChange={handlePropertyChange}
            />
          </div>
          <div className="px-5">
            <input
              className="py-1 px-1"
              placeholder="Password"
              name="password"
              type="password"
              value={registerInput.password || ""}
              onChange={handlePropertyChange}
            />
          </div>
          <div className="px-5">
            <input
              className="py-1 px-1"
              placeholder="Confirm Password"
              name="confirmPassword"
              type="password"
              value={registerInput.confirmPassword || ""}
              onChange={handlePropertyChange}
            />
          </div>
          <div className="px-5">
            <input
              className="py-1 px-1"
              placeholder="First name"
              name="firstName"
              value={registerInput.firstName || ""}
              onChange={handlePropertyChange}
            />
          </div>
          <div className="px-5">
            <input
              className="py-1 px-1"
              placeholder="Last name"
              name="lastName"
              value={registerInput.lastName || ""}
              onChange={handlePropertyChange}
            />
          </div>
          <div className="px-5">
            <input
              className="py-1 px-1"
              placeholder="Email"
              name="email"
              value={registerInput.email || ""}
              onChange={handlePropertyChange}
            />
          </div>
          <div className="px-5">
            <input
              className="py-1 px-1"
              placeholder="Balance"
              name="balance"
              value={registerInput.balance || ""}
              onChange={handlePropertyChange}
            />
          </div>
          <div className="px-5">
            <select
              className="w-full"
              id="currency"
              name="currency"
              value={registerInput.currency || ""}
              onChange={handleSelectChange}
            >
              <option value="RSD">RSD</option>
              <option value="EUR">EUR</option>
            </select>
          </div>
          <button className="bg-teal-500 hover:bg-teal-700 text-white font-bold py-1 px-3 rounded">
            Confirm Email
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

export default RegisterPage;
