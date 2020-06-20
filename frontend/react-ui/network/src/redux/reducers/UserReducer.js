import { GET_USER } from "../../actions/ActionNames";

const defState = {
  userDetails: {},
};

export const userReducer = (state = defState, action) => {
  switch (action.type) {
    case GET_USER:
      return { ...state, userDetails: action.payload };
    default:
      return state;
  }
};
