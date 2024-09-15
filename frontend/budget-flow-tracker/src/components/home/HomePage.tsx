import React, { useEffect, useState } from "react";
import fetchTransactionsAsync from "../../services/transaction/fetchTransactions";
import { useNavigate } from "react-router-dom";
import { NEW_TRANSACTION_PAGE, LOGIN_PAGE } from "../routes/AppRouter";
import deleteTransactionAsync from "../../services/transaction/deleteTransaction";
import UserProfile from "../../models/userprofile/UserProfile";
import fetchUserProfileAsync from "../../services/userprofile/fetchUserProfile";

type Transaction = {
  id: number;
  name: string;
  date: string;
  transactionType: string;
  amount: number;
  description: string;
  building: string;
};

type ApiResponse = {
  content: Transaction[];
  number: number;
  size: number;
  totalElements: number;
  totalPages: number;
  first: boolean;
  last: boolean;
};

const HomePage = () => {
  const [date, setDate] = useState(new Date());
  const [formattedDate, setFormattedDate] = useState("");
  const [pageNumber, setPageNumber] = useState(0);
  const [pageSize, setPageSize] = useState(5);
  const [transactions, setTransactions] = useState<Transaction[]>([]);
  const [user, setUser] = useState<UserProfile>();
  const [apiResponse, setApiResponse] = useState<ApiResponse>();
  const [loading, setLoading] = useState(true);
  const [selectedTransactionId, setSelectedTransactionId] = useState(-1);
  const navigate = useNavigate();

  useEffect(() => {
    fetchUserProfileData();
  }, []);

  useEffect(() => {
    fetchTransactions();
  }, [pageNumber, pageSize]);

  useEffect(() => {
    setFormattedDate(
      `${date.getDate()}.${date.getMonth() + 1}.${date.getFullYear()}`
    );
    fetchTransactions();
  }, [date]);

  const handlePageNumber = (number: number) => {
    setPageNumber((prevNumber) => prevNumber + number);
  };

  const handleAddTransaction = () => {
    navigate(NEW_TRANSACTION_PAGE);
  };

  const handleDeleteTransaction = async () => {
    if (selectedTransactionId === -1) {
      alert("Please select a transaction to delete.");
    } else {
      const status = await deleteTransactionAsync(selectedTransactionId);
      if (status !== 200) alert("There is an error.");
      else {
        alert("Transaction deleted.");
        fetchTransactions();
        fetchUserProfileData();
      }
    }
  };

  const handleDateChange = (number: number) => {
    setDate((prevDate) => {
      const newDate = new Date(prevDate);
      newDate.setDate(newDate.getDate() + number);
      return newDate;
    });
  };

  const handleSelectedTransaction = (id: number) => {
    if (id === selectedTransactionId) {
      setSelectedTransactionId(-1);
    } else setSelectedTransactionId(id);
  };

  const fetchTransactions = async () => {
    const responseJson = await fetchTransactionsAsync(
      `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`,
      pageNumber,
      pageSize
    );
    if (responseJson != null) {
      setApiResponse(responseJson);
      setTransactions(responseJson.content);
      setLoading(false);
    } else {
      alert("There is an error.");
    }
  };

  const fetchUserProfileData = async () => {
    const responseJson = await fetchUserProfileAsync();
    if (responseJson != null) {
      setUser(responseJson);
    } else {
      alert("There is an error.");
    }
  };

  if (loading) {
    return (
      <div className="text-xl flex flex-col items-center justify-center min-h-screen rounded-lg border-2 border-teal-600">
        Loading...
      </div>
    );
  }

  return (
    <div className="flex flex-col items-center justify-center min-h-screen rounded-lg pt-10">
      <div className="flex justify-between items-center text-base w-80">
        <div className="text-xl mb-2">
          {user?.firstname} {user?.lastname}
        </div>
        <div className="text-xs mb-2">
          <a
            href={LOGIN_PAGE}
            rel="noopener noreferrer"
            className="text-blue-500 hover:text-blue-700"
            onClick={() => {
              localStorage.removeItem("token");
            }}
          >
            Logout
          </a>
        </div>
      </div>

      <div className="text-xl flex-col text-base text-center mb-10 pb-2 pt-2 bg-teal-500 border-2 border-teal-600 w-80">
        <div className="flex justify-between px-3 text-base">
          <div>Balance:</div>
          <div className="flex">
            <div
              onClick={() => handleDateChange(-1)}
              className="px-2 bg-teal-100 hover:bg-teal-200 cursor-pointer rounded-full"
            >
              {"<"}
            </div>
            <div className="px-2">{formattedDate}</div>
            <div
              onClick={() => handleDateChange(1)}
              className={` ${
                `${new Date().getDate()}.${
                  new Date().getMonth() + 1
                }.${new Date().getFullYear()}` === formattedDate
                  ? "bg-gray-300 pointer-events-none opacity-50"
                  : "bg-teal-100 hover:bg-teal-200 cursor-pointer"
              }  px-2 bg-teal-100 rounded-full`}
            >
              {">"}
            </div>
          </div>
        </div>
        <div className="flex justify-between px-3 text-base">
          <div>
            {user?.balance} {user?.currency}
          </div>
        </div>
      </div>

      {transactions.length === 0 && (
        <div className="text-base h-[350px]">
          No transactions for this date.
        </div>
      )}
      {transactions.length > 0 && (
        <div className="h-[350px]">
          {transactions?.map((transaction) => (
            <div
              key={transaction.id}
              onClick={() => handleSelectedTransaction(transaction.id)}
            >
              <div
                className={`cursor-pointer text-xl flex-col text-base text-center pb-2 pt-2 border-2 border-teal-600 w-80 ${
                  selectedTransactionId === transaction.id
                    ? "bg-teal-200"
                    : "bg-teal-50"
                }`}
              >
                <div className="flex justify-between px-3 text-base">
                  <div>{transaction.name}</div>
                  <div>
                    {transaction.transactionType === "INCOME"
                      ? transaction.amount + " " + user?.currency
                      : "-" + transaction.amount + " " + user?.currency}{" "}
                  </div>
                </div>
                <div className="flex justify-between px-3 text-base">
                  <div>{transaction.date.split("T")[1].split(".")[0]}</div>
                  <div>{transaction.building}</div>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}

      <div className="text-xl flex-col text-base text-center pb-2 pt-2 bg-teal-50 mt-2 w-80 rounded-full">
        <div className="flex justify-between px-3 text-base">
          <div
            onClick={() => handlePageNumber(-1)}
            className={` ${
              apiResponse?.first == true
                ? "bg-gray-100 pointer-events-none opacity-65"
                : "bg-teal-100 hover:bg-teal-200 cursor-pointer"
            }  rounded-full p-1 px-3`}
          >
            {"<"}
          </div>
          <div
            onClick={() => handlePageNumber(1)}
            className={` ${
              apiResponse?.last == true
                ? "bg-gray-100 pointer-events-none opacity-65"
                : "bg-teal-100 hover:bg-teal-200 cursor-pointer"
            }  rounded-full p-1 px-3 `}
          >
            {">"}
          </div>
        </div>
      </div>
      <div className="text-xl flex text-base pb-2 pt-2 w-1/8 gap-x-3">
        <div className="cursor-pointer flex justify-center px-3 text-base p-1 rounded-lg bg-teal-100 hover:bg-teal-200">
          <div onClick={handleAddTransaction}>ADD NEW</div>
        </div>
        <div className="cursor-pointer flex justify-center px-3 text-base p-1 rounded-lg bg-teal-100 hover:bg-teal-200">
          <div onClick={handleDeleteTransaction}>DELETE SELECTED</div>
        </div>
      </div>
    </div>
  );
};

export default HomePage;
