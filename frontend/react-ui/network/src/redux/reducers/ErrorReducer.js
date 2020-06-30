import { ERROR, CLEAR_ERROR } from "../../actions/ActionNames";

const defState = {
  errMsg: "",
};

export const errorReducer = (state = defState, action) => {
  switch (action.type) {
    case CLEAR_ERROR:
      return {
        ...state,
        ...defState,
      };
    case ERROR:
      return {
        ...state,
        errMsg: action.payload,
      };
    default:
      return state;
  }
};
