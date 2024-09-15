import { FormEvent, useState } from "react";
import React from "react";
import TransactionInput from "../../models/transaction/TransactionInput";
import { HOME_PAGE } from "../routes/AppRouter";
import { useNavigate } from "react-router-dom";
import createTransactionAsync from "../../services/transaction/createTransaction";

const CreateNewTransactionPage = () => {
  const [transactionInput, setTransactionInput] = useState<TransactionInput>({
    transactionType: "EXPENSE",
  } as TransactionInput);

  const navigate = useNavigate();

  const handleSubmit = async (event: FormEvent) => {
    event.preventDefault();
    const responsestatus = await createTransactionAsync(
      transactionInput.name,
      transactionInput.date,
      transactionInput.transactionType,
      transactionInput.amount,
      transactionInput.building
    );
    if (responsestatus === 200) {
      navigate(HOME_PAGE);
    }
  };
  const handlePropertyChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setTransactionInput((prevUserInput) => {
      return {
        ...prevUserInput,
        [event.target.name]: event.target.value,
      } as TransactionInput;
    });
  };
  const handleSelectChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setTransactionInput((prevUserInput) => {
      return {
        ...prevUserInput,
        [event.target.name]: event.target.value,
      } as TransactionInput;
    });
  };
  return (
    <form onSubmit={handleSubmit}>
      <div className="flex items-center justify-center min-h-screen">
        <div className="text-xl flex-col text-base text-center pb-5 pt-5 bg-teal-100 rounded-lg space-y-2">
          <div className="px-5 text-base text-left">Add new transaction:</div>
          <div className="px-5">
            <input
              className="py-1 px-1 w-full"
              placeholder="Name of the transaction"
              name="name"
              value={transactionInput.name || ""}
              onChange={handlePropertyChange}
            />
          </div>
          <div className="px-5">
            <input
              className="py-1 px-1"
              placeholder="Date (YYYY-MM-DD)"
              type="datetime-local"
              name="date"
              value={transactionInput.date || ""}
              onChange={handlePropertyChange}
            />
          </div>
          <div className="px-5">
            <select
              className="w-full"
              id="transactionType"
              name="transactionType"
              value={transactionInput.transactionType || ""}
              onChange={handleSelectChange}
            >
              <option value="EXPENSE">Expense</option>
              <option value="INCOME">Income</option>
            </select>
          </div>
          <div className="px-5">
            <input
              className="py-1 px-1 w-full"
              placeholder="Amount"
              name="amount"
              value={transactionInput.amount || ""}
              onChange={handlePropertyChange}
            />
          </div>
          <div className="px-5">
            <input
              className="py-1 px-1 w-full"
              placeholder="Place"
              name="building"
              value={transactionInput.building || ""}
              onChange={handlePropertyChange}
            />
          </div>
          <button className="bg-teal-500 hover:bg-teal-700 text-white font-bold py-1 px-3 rounded">
            Create Transaction
          </button>
          <div className="text-xs">
            <a
              href={HOME_PAGE}
              rel="noopener noreferrer"
              className="text-blue-500 hover:text-blue-700"
            >
              Back to Home Page
            </a>
          </div>
        </div>
      </div>
    </form>
  );
};

export default CreateNewTransactionPage;
