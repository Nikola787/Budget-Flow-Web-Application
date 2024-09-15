export default async function fetchTransactionsAsync(
  date: string,
  pageNumber: number,
  pageSize: number
) {
  const requestBuilder = {
    method: "GET",
    headers: {
      Authorization: localStorage.getItem("token") + "",
    },
  };
  const url = `http://localhost:8080/api/v1/transactions?date=${date}&page=${pageNumber}&size=${pageSize}`;
  const response = await fetch(url, requestBuilder);
  if (response.status === 403) {
    return null;
  }
  const data = await response.json();
  return data;
}
