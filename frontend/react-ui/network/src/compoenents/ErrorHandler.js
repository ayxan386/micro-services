import React, { Component } from "react";
import { connect } from "react-redux";

export class ErrorHandler extends Component {
  render() {
    return (
      <>
        {this.props.msg ? (
          <div id='error-handler'>
            <div className='error-holder'>
              <b>{this.props.msg}</b>
            </div>
          </div>
        ) : null}
      </>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    msg: state.error.errMsg,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {};
};

export default connect(mapStateToProps, mapDispatchToProps)(ErrorHandler);
