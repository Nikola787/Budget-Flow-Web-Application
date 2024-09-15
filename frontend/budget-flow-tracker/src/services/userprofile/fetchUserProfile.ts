export default async function fetchUserProfileAsync() {
  const requestBuilder = {
    method: "GET",
    headers: {
      Authorization: localStorage.getItem("token") + "",
    },
  };
  const url = "http://localhost:8080/api/v1/userprofile";
  const response = await fetch(url, requestBuilder);
  if (response.status === 403) {
    return null;
  }
  const data = await response.json();
  return data;
}
