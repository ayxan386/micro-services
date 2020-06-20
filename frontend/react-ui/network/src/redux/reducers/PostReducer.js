import { LOAD_POSTS, ADD_POST } from "../../actions/ActionNames";

const defState = {
  posts: [],
};

export const postReducer = (state = defState, actions) => {
  switch (actions.type) {
    case LOAD_POSTS:
      return { ...state, posts: actions.payload.posts };
    case ADD_POST:
      return {
        ...state,
        posts: [actions.payload.post, ...state.posts],
      };
    default:
      return state;
  }
};
