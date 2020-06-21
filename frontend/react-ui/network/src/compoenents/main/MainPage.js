import React, { Component } from "react";
import { Helmet } from "react-helmet";
import { loadPosts } from "../../actions/PostActions";
import Post from "./post/Post";
import { connect } from "react-redux";
import "../../styles/main.css";
import { Redirect } from "react-router-dom";
import UserDetailsPosterWrapper from "./user details/UserDetailsPosterWrapper";
import PostDetailsCommenterWrapper from "./post/PostDetailsCommenterWrapper";

export class MainPage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      activePostId: -1,
      postDetails: false,
    };
  }
  componentDidMount() {
    this.props.loadPosts();
  }

  callback = (post) => {
    this.setState({
      activePostId: post,
      postDetails: true,
    });
  };

  normalState = () => {
    this.setState({
      activePostId: -1,
      postDetails: false,
    });
  };

  render() {
    return (
      <>
        <Helmet>
          <title>Home</title>
        </Helmet>
        <div className='whole'>
          {!this.props.isLogged ? <Redirect to='/login'></Redirect> : null}
          <div className='main-post-container'>
            <div className='main-posts'>
              {this.props.posts &&
                this.props.posts.map((post) => (
                  <Post
                    key={post.id}
                    id={post.id}
                    title={post.title}
                    body={post.body}
                    author={post.author}
                    onClickCallBack={this.callback}></Post>
                ))}
            </div>
          </div>
          {!this.state.postDetails ? (
            <UserDetailsPosterWrapper />
          ) : (
            <PostDetailsCommenterWrapper
              postId={this.state.activePostId}
              posts={this.props.posts}
              closeDetails={this.normalState}
            />
          )}
        </div>
      </>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    posts: state.posts.posts,
    isLogged: state.auth.isLogged,
    pageSize: 5,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    loadPosts: () => dispatch(loadPosts()),
  };
};
export default connect(mapStateToProps, mapDispatchToProps)(MainPage);
