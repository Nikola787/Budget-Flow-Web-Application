export default async function deleteTransactionAsync(id: Number) {
  const requestBuilder = {
    method: "DELETE",
    headers: {
      Authorization: localStorage.getItem("token") + "",
      "Content-Type": "application/json",
    },
  };

  const response = await fetch(
    `http://localhost:8080/api/v1/transactions/${id}`,
    requestBuilder
  );

  return response.status;
}
