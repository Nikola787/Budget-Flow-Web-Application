export default async function authenticateUserAsync(
  username: string,
  password: string
) {
  const requestBuilder = {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      username: username,
      password: password,
    }),
  };

  const response = await fetch(
    "http://localhost:8080/api/v1/auth/authenticate",
    requestBuilder
  );

  const data = await response.json();

  if (response.status === 200) {
    localStorage.setItem("token", "Bearer " + data.token);
  }

  return response.status;
}
