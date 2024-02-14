import { createGlobalStyle } from "styled-components";

const GlobalStyle = createGlobalStyle`
  body {
    box-sizing: border-box;
    outline: 0;
    margin: 0;
    padding: 0;
    border: 0;
  }
  .error-message {
    color: red;
    font-weight: regular;
    font-size: 16px;
  }
`;

export default GlobalStyle;
