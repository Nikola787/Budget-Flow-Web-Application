export default class TransactionInput {
  name: string;
  date: string;
  transactionType: string;
  amount: string;
  building: string;

  constructor(
    name: string,
    date: string,
    transactionType: string,
    amount: string,
    building: string
  ) {
    this.name = name;
    this.date = date;
    this.transactionType = transactionType;
    this.amount = amount;
    this.building = building;
  }
}
