import { LOGOUT, ERROR } from "./ActionNames";

export const handleError = (err) => {
  switch (err.message) {
    case "Token expired!":
      return {
        type: LOGOUT,
      };
    default:
      return {
        type: ERROR,
      };
  }
};
