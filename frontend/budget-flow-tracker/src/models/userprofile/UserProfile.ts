export default class UserProfile {
  firstname: string;
  lastname: string;
  balance: number;
  currency: string;

  constructor(
    firstname: string,
    lastname: string,
    balance: number,
    currency: string
  ) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.balance = balance;
    this.currency = currency;
  }
}
