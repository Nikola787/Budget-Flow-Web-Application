function validateName(name: string): string | null {
  if (!name || !/^[a-zA-Z\s]{3,30}$/.test(name)) {
    return "Name must contain only letters and spaces, and be between 3 and 30 characters long.";
  }
  return null;
}

function validateDate(date: string): string | null {
  if (!date) {
    return "Date cannot be null.";
  }
  return null;
}

function validateTransactionType(transactionType: string): string | null {
  const validTypes = ["INCOME", "EXPENSE"];
  if (!transactionType || !validTypes.includes(transactionType)) {
    return "Transaction type must be 'EXPENSE' or 'INCOME'.";
  }
  return null;
}

function validateAmount(amount: string): string | null {
  if (!amount || isNaN(Number(amount)) || Number(amount) < 0) {
    return "Amount must be a positive value.";
  }
  return null;
}

function validateBuilding(building: string): string | null {
  if (!building || !/^[a-zA-Z\s]{3,30}$/.test(building)) {
    return "Location must contain only letters and spaces, and be between 3 and 30 characters long.";
  }
  return null;
}

export default async function createTransactionAsync(
  name: string,
  date: string,
  transactionType: string,
  amount: string,
  building: string
) {
  const validationErrors = [
    validateName(name),
    validateDate(date),
    validateTransactionType(transactionType),
    validateAmount(amount),
    validateBuilding(building),
  ].filter((error) => error !== null);

  if (validationErrors.length > 0) {
    alert("Please fix the following errors:\n" + validationErrors.join("\n"));
    return null;
  }

  const requestBuilder = {
    method: "POST",
    headers: {
      Authorization: localStorage.getItem("token") + "",
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      name: name,
      date: date,
      transactionType: transactionType,
      amount: amount,
      building: building,
    }),
  };

  const response = await fetch(
    "http://localhost:8080/api/v1/transactions",
    requestBuilder
  );

  return response.status;
}
