export default async function activateAccountAsync(confirmationCode: string) {
  const requestBuilder = {
    method: "GET",
    headers: { "Content-Type": "application/json" },
  };
  const url = `http://localhost:8080/api/v1/auth/activate-account?token=${confirmationCode}`;
  const response = await fetch(url, requestBuilder);

  return response.status;
}
