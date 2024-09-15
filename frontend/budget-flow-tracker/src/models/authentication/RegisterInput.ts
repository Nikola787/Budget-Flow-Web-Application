export default class RegisterInput {
  username: string;
  password: string;
  confirmPassword: string;
  firstName: string;
  lastName: string;
  email: string;
  balance: string;
  currency: string;

  constructor(
    username: string,
    password: string,
    confirmPassword: string,
    firstName: string,
    lastName: string,
    email: string,
    balance: string,
    currency: string
  ) {
    this.username = username;
    this.password = password;
    this.confirmPassword = confirmPassword;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.balance = balance;
    this.currency = currency;
  }
}
