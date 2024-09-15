import React, { FormEvent, useEffect, useState } from "react";
import LoginInput from "../../models/authentication/LoginInput";
import { useNavigate } from "react-router-dom";
import { HOME_PAGE, REGISTER_PAGE } from "../routes/AppRouter";

import authenticateUserAsync from "../../services/auth/authenticateUser";

const LoginPage = () => {
  const [loginInput, setLoginInput] = useState<LoginInput>({} as LoginInput);

  const navigate = useNavigate();

  useEffect(() => {
    if (localStorage.getItem("token")) {
      navigate(HOME_PAGE);
    }
  }, []);

  const handleSubmit = async (event: FormEvent) => {
    event.preventDefault();

    const responsestatus = await authenticateUserAsync(
      loginInput.username,
      loginInput.password
    );
    if (responsestatus === 200) {
      navigate(HOME_PAGE);
    } else {
      alert("Invalid credentials");
    }
  };

  const handlePropertyChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setLoginInput((prevUserInput) => {
      return {
        ...prevUserInput,
        [event.target.name]: event.target.value,
      } as LoginInput;
    });
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="flex items-center justify-center min-h-screen">
        <div className="text-xl flex-col text-base text-center pb-5 pt-5 bg-teal-100 rounded-lg space-y-2">
          <div className="px-3 text-base text-left">
            Please enter your credentials:
          </div>
          <div className="px-3">
            <input
              className="py-1 px-1"
              placeholder="Username"
              name="username"
              value={loginInput.username || ""}
              onChange={handlePropertyChange}
            />
          </div>
          <div className="px-3">
            <input
              className="py-1 px-1"
              placeholder="Password"
              name="password"
              type="password"
              value={loginInput.password || ""}
              onChange={handlePropertyChange}
            />
          </div>
          <button className="bg-teal-500 hover:bg-teal-700 text-white font-bold py-1 px-3 rounded">
            Sign in
          </button>
          <div className="text-xs">
            Don't have an account?&nbsp;
            <a
              href={REGISTER_PAGE}
              rel="noopener noreferrer"
              className="text-blue-500 hover:text-blue-700"
            >
              Register
            </a>
          </div>
        </div>
      </div>
    </form>
  );
};

export default LoginPage;
