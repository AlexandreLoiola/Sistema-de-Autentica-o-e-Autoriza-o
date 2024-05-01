import styled from "styled-components";
import { Form, Button, ButtonProps } from "react-bootstrap";
import { HiMagnifyingGlass } from "react-icons/hi2";
import { BsEyeFill, BsEyeSlashFill } from "react-icons/bs";

export const StyledFormGroup = styled(Form.Group)`
  margin-top: 0;
  margin-bottom: 0 !important; 
  width: 100%;
`;

export const StyledFormLabel = styled(Form.Label)`
  font-size: 1.2rem;
  color: #333;
`;

export const StyledFormControl = styled(Form.Control)`
  font-size: 1rem;
  background-color: white;
  padding: 0.5rem .5rem;
  border: 1px solid lightgray;
  width: 100%;
`;

export const StyledFormText = styled(Form.Text)`
  font-size: 0.8rem;
  color: #777;
`;

export const StyledFormControlReadonly = styled(Form.Control)`
  border: 1px solid #ccc;
  font-size: 1rem;
  background-color: #d3d3d3;
  width: 220px;
  padding: 0.5rem 1rem;
  cursor: not-allowed;

  &:hover {
    background-color: #a9a9a9;
  }
`;

export const FormContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: left;
  align-items: center;
  width: 100%;
`;

export const MagnifyingGlassIcon = styled(HiMagnifyingGlass)`
  color: white;
  font-size: 26px;
  font-weight: 700;
`;

export const EyeCloseIcon = styled(BsEyeFill)`
  color: #777;
  font-size: 20px;
  font-weight: 700;
`;
export const EyeOpenIcon = styled(BsEyeSlashFill)`
  color: #777;
  font-size: 20px;
  font-weight: 700;
`;

export const StyledFormButton = styled(Button)`
  display: flex;
  width: 280px;
  height: 50px;
  justify-content: center;
  margin: auto;
  padding: 12px 1px;
  font-size: 16px;
  font-weight: 500;
  background-color: #6db33f;
  border: none;
  border-radius: 4px;

  &:hover {
    background-color: #467025;
    transition: font-size 3s;
  }

  &:active {
    box-shadow: inset 0 3px 8px rgba(0, 0, 0, 0.6);
    transform: translateY(2px);
    background-color: #467025 !important;
    transition: font-size 3s;
  }

  &:focus-visible {
    box-shadow: inset 0 3px 8px rgba(0, 0, 0, 0.6);
    transform: translateY(2px);
    background-color: #467025;
    transition: font-size 3s;
  }
`;

export const ButtonSeePass = styled(Button)<ButtonProps>`
  display: flex;
  width: auto;
  height: 50px;
  padding: 12px 2%;
  font-size: 16px;
  font-weight: 500;
  margin-top: 40px;
  border: none;
  border-radius: 4px;
  background: transparent;
  box-shadow: none;
  margin-top: -44px;

  &:hover {
    background: transparent;
    transition: font-size 3s;
    font-size: 18px;
  }

  &:active {
    background-color: transparent;
    transform: translateY(2px);
  }
`;
