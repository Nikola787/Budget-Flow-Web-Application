import React, { Fragment } from "react";
import { Route, Routes } from "react-router-dom";
import HomePage from "../home/HomePage";
import LoginPage from "../login/LoginPage";
import RegisterPage from "../register/RegisterPage";
import EmailConfirmationPage from "../register/EmailConfirmationPage";
import CreateNewTransactionPage from "../transaction/CreateNewTransactionPage";

export const LOGIN_PAGE = "/";
export const HOME_PAGE = "/home";
export const REGISTER_PAGE = "/register";
export const EMAIL_CONFIRMATION_PAGE = "/email-confirmation";
export const NEW_TRANSACTION_PAGE = "/create-new-transaction";

const AppRouter = () => {
  return (
    <Routes>
      <Fragment>
        <Route path={HOME_PAGE} element={<HomePage />} />
        <Route path={LOGIN_PAGE} element={<LoginPage />} />
        <Route path={REGISTER_PAGE} element={<RegisterPage />} />
        <Route
          path={EMAIL_CONFIRMATION_PAGE}
          element={<EmailConfirmationPage />}
        />
        <Route
          path={NEW_TRANSACTION_PAGE}
          element={<CreateNewTransactionPage />}
        />
      </Fragment>
    </Routes>
  );
};

export default AppRouter;
