import React from "react";
import {
  StyledFormGroup,
  StyledFormLabel,
  StyledFormControl,
} from "./styles";

interface IProps {
  label: string;
  placeHolder: string;
  message: string;
  value?: string | number;
  style?: React.CSSProperties;
  onInputChange: (value: string) => void;
}

const TextForm: React.FC<IProps> = ({
  label,
  placeHolder,
  message,
  value,
  onInputChange,
}) => {
  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    onInputChange(event.target.value);
  };

  return (
    <StyledFormGroup controlId="formBasicEmail">
      <StyledFormLabel>{label}</StyledFormLabel>
      <StyledFormControl
        type="input"
        placeholder={placeHolder}
        value={value}
        onChange={handleInputChange}
      />
      <p className="error-message">{message}</p>
    </StyledFormGroup>
  );
};

export default TextForm;
