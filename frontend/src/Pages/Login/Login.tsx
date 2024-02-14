import React from "react";
import {
  Container,
  ContainerLeft,
  ContainerRight,
  StyledLink,
  StyledTitle,
} from "./styles";
import PasswordInput from "../../Components/Forms/PasswordForm";
import TextForm from "../../Components/Forms/TextForm";
import { FormContainer, StyledFormButton } from "../../Components/Forms/styles";
import CheckboxForm from "../../Components/Forms/CheckboxForm";

const Login: React.FC = () => {
  return (
    <Container>
      <ContainerLeft>
        <StyledTitle>Login</StyledTitle>
        <FormContainer>
          <TextForm
            label={"Usuário"}
            placeHolder={""}
            message={""}
            onInputChange={function (value: string): void {
              throw new Error("Function not implemented.");
            }}
          />
          <PasswordInput
            label={"Senha"}
            placeHolder={""}
            message={""}
            onInputChange={function (value: string): void {
              throw new Error("Function not implemented.");
            }}
          />
          <StyledFormButton style={{ width: "100%" }}>ENTRAR</StyledFormButton>
          <div
            style={{
              display: "flex",
              flexDirection: "row",
              width: "100%",
              marginTop: "1.5vh",
              justifyContent: "space-between",
            }}
          >
            <CheckboxForm
              label={"Lembrar-me"}
              message={""}
              onInputChange={function (value: string): void {
                throw new Error("Function not implemented.");
              }}
            />
            <StyledLink to="/forgot-password">Esqueceu a senha?</StyledLink>
          </div>
          <div
            style={{
              width: "86%",
              borderTop: "1px solid #00000058",
              marginTop: "4vh",
              marginBottom: "6vh",
            }}
          ></div>
          <span >
            Não possui conta? <StyledLink to="/signup">Clique aqui</StyledLink>
          </span>
        </FormContainer>
      </ContainerLeft>
      <ContainerRight />
    </Container>
  );
};
export default Login;
