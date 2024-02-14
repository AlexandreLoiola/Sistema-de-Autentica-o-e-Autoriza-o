import React from "react";

import TextForm from "../../Components/Forms/TextForm";
import { FormContainer, StyledFormButton } from "../../Components/Forms/styles";
import { Container, ContainerLeft, StyledList, StyledListItem, StyledSubTitle, StyledTitle } from "./styles";

const RecoverPass: React.FC = () => {
  return (
    <Container>
      <ContainerLeft>
        <StyledTitle>Redefinir Senha</StyledTitle>
        <StyledSubTitle>
          Siga os seguintes passos para recuperar sua senha:
        </StyledSubTitle>
        <StyledList>
          <StyledListItem>
            1. Informe seu nome de usuário ou e-mail;
          </StyledListItem>
          <StyledListItem>
            2. Aguarde o recebimento de um formulário em seu email;
          </StyledListItem>
          <StyledListItem>
            3. Siga os passos do e-mail e crie uma senha forte.
          </StyledListItem>
        </StyledList>
        <FormContainer>
          <TextForm
            label={"Usuário ou E-mail"}
            placeHolder={""}
            message={""}
            onInputChange={function (value: string): void {
              throw new Error("Function not implemented.");
            }}
          />
          <StyledFormButton style={{ width: "100%" }}>
            RECUPERAR SENHA
          </StyledFormButton>
        </FormContainer>
      </ContainerLeft>
      <ContainerRight />
    </Container>
  );
};
export default RecoverPass;
