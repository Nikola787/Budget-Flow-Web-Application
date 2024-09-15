import "./App.css";
import AppRouter from "./components/routes/AppRouter";
import { BrowserRouter } from "react-router-dom";
import React from "react";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const App = () => {
  return (
    <BrowserRouter>
      <div className="">
        <AppRouter />
      </div>
    </BrowserRouter>
  );
};

export default App;
