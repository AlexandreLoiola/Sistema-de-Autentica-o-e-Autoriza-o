import { useState } from "react";
import Form from "react-bootstrap/Form";

interface IProps {
  label: string;
  message: string;
  style?: React.CSSProperties;
  onInputChange: (value: string) => void;
}

const CheckboxForm: React.FC<IProps> = ({ label, message, onInputChange }) => {

  const [checked, setChecked] = useState(false);

  const handleCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setChecked(event.target.checked);
    onInputChange(event.target.checked ? "checked" : "unchecked");
  };

  return (
    <Form>
      {["checkbox"].map((type) => (
        <div key={`default-${type}`} className="mb-3">
          <Form.Check
            id={`default-${type}`}
            label={label}
            checked={checked}
            onChange={handleCheckboxChange}
          />
          <p className="error-message">{message}</p>
        </div>
      ))}
    </Form>
  );
};
export default CheckboxForm;
