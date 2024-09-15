function validateUsername(username: string) {
  return /^[a-zA-Z]{3,30}$/.test(username)
    ? ""
    : "Username must contain only letters.";
}

function validatePasswords(password: string, confirmPassword: string) {
  const isValid = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/.test(
    password
  );
  if (!isValid) {
    return "Password must have at least 8 characters, including one uppercase letter, one lowercase letter, and one number.";
  }
  if (password !== confirmPassword) {
    return "Password and Confirm Password do not match.";
  }
  return "";
}

function validateFirstName(firstName: string) {
  return /^[a-zA-Z]{3,30}$/.test(firstName)
    ? ""
    : "First name must contain only letters and be between 3 and 30 characters long.";
}

function validateLastName(lastName: string) {
  return /^[a-zA-Z]{3,30}$/.test(lastName)
    ? ""
    : "Last name must contain only letters and be between 3 and 30 characters long.";
}

function validateEmail(email: string) {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email) ? "" : "Email must be valid.";
}

function validateBalance(balance: string) {
  return !isNaN(parseFloat(balance)) && parseFloat(balance) > 0
    ? ""
    : "Balance must be a positive number.";
}

function validateCurrency(currency: string) {
  return ["EUR", "RSD"].includes(currency)
    ? ""
    : "Currency must be either EUR or RSD.";
}

export default async function registerUserAsync(
  username: string,
  password: string,
  confirmPassword: string,
  firstName: string,
  lastName: string,
  email: string,
  balance: string,
  currency: string
) {
  const errorMessages = [
    validateUsername(username),
    validatePasswords(password, confirmPassword),
    validateFirstName(firstName),
    validateLastName(lastName),
    validateEmail(email),
    validateBalance(balance),
    validateCurrency(currency),
  ].filter((msg) => msg !== "");

  if (errorMessages.length > 0) {
    alert("Please fix the following errors:\n\n" + errorMessages.join("\n"));
    return null;
  }

  const requestBuilder = {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      username: username,
      password: password,
      firstname: firstName,
      lastname: lastName,
      email: email,
      balance: parseFloat(balance),
      currency: currency,
    }),
  };

  try {
    const response = await fetch(
      "http://localhost:8080/api/v1/auth/register",
      requestBuilder
    );

    if (!response.ok) {
      const data = await response.json();
      const serverErrors = Object.entries(data.messages)
        .map(([field, message]) => `${field}: ${message}`)
        .join("\n");
      alert("Please fix the following server-side errors:\n\n" + serverErrors);
      return null;
    }

    return response.status;
  } catch (error) {
    alert("An error occurred while registering. Please try again.");
    return null;
  }
}
