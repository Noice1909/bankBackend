import React from 'react'
import './Register.css'
import { Link } from 'react-router-dom'
import { useFormik } from 'formik';
import * as Yup from 'yup';

const Register = (props) => {
    const formik = useFormik({
        initialValues: {
          fullName: '',
          email: '',
          password: '',
          confirmPassword: '',
          phoneNumber:'',
          address:'',
        },
        validationSchema: Yup.object({
            fullName: Yup.string().min(3, 'Username must be at least 3 characters').required('Username is required.'),
            email: Yup.string().email('Invalid email address.').required('Email is required.'),
            password: Yup.string().required('Password is required.'),
            confirmPassword: Yup.string()
                .oneOf([Yup.ref('password'), null], 'Passwords must match')
                .required('Confirm Password is required.'),
            phoneNumber: Yup.string().matches(/^(?:\+?\d{1,3}\s?)?\d{10}$/, 'Invalid phone number').required('Phone number is required'),
            birthDate: Yup.date().max(new Date(), 'Birth date cannot be in the future').required('Birth date is required'),
            address: Yup.string().required('Address is required.'),
            city: Yup.string().required('City is required.'),
            region: Yup.string().required('Region is required.'),
            postalCode : Yup.string()
            .matches(/^\d{6}$/, 'Postal code must be exactly 6 digits').required('Postal code is required'),
        }),
        onSubmit: (values) => {
          // Submit logic
          // send data to data base on successful sent redirect to dash board else show error page try again
          const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(values)
        };
        fetch('http://localhost:8080/api/v1/sendCustomer', requestOptions)
            .then(response => response.json())
            .then(data => {
              console.log(data)
              console.log(values)
            });
        },
      });
  return (
    <section className="container">
      <header>Registration Form</header>
      <form onSubmit={formik.handleSubmit} className="form">
        <div className="input-box">
            <label>Full Name</label>
            <input type="text" name='fullName' value={formik.values.fullName} onChange={formik.handleChange} onBlur={formik.handleBlur} placeholder="Enter full name" required />
            {formik.touched.fullName && formik.errors.fullName && (<span className="error">{formik.errors.fullName}</span>)}
        </div>
        <div className="input-box">
            <label>Email Address</label>
            <input type="text" name='email' value={formik.values.email} onChange={formik.handleChange} onBlur={formik.handleBlur} placeholder="Enter email address" required />
            {formik.touched.email && formik.errors.email && (<span className="error">{formik.errors.email}</span>)}
        </div>
        <div className="column">
          <div className="input-box">
            <label>Phone Number</label>
            <input type="text" name='phoneNumber' value={formik.values.phoneNumber} onChange={formik.handleChange} onBlur={formik.handleBlur} placeholder="Enter phone number" required />
            {formik.touched.phoneNumber && formik.errors.phoneNumber && (<span className="error">{formik.errors.phoneNumber}</span>)}
          </div>
          <div className="input-box">
            <label>Birth Date</label>
            <input type="date" name='birthDate' value={formik.values.birthDate} onChange={formik.handleChange} onBlur={formik.handleBlur} placeholder="Enter birth date" required />
            {formik.touched.birthDate && formik.errors.birthDate && (<span className="error">{formik.errors.birthDate}</span>)}
          </div>
        </div>
        <div className="column">
          <div className="input-box">
            <label>Password</label>
            <input type="password" name='password' value={formik.values.password} onChange={formik.handleChange} onBlur={formik.handleBlur} placeholder="Enter password" required />
            {formik.touched.password && formik.errors.password && (<span className="error">{formik.errors.password}</span>)}
          </div>
          <div className="input-box">
            <label>Confirm Password</label>
            <input type="confirmPassword" name='confirmPassword' value={formik.values.confirmPassword} onChange={formik.handleChange} onBlur={formik.handleBlur} placeholder="Enter confirm password" required />
            {formik.touched.confirmPassword && formik.errors.confirmPassword && (<span className="error">{formik.errors.confirmPassword}</span>)}
          </div>
        </div>
        <div className="gender-box">
          <h3>Gender</h3>
          <div className="gender-option">
            <div className="gender">
              <input type="radio" id="check-male" name="gender" checked />
              <label for="check-male">male</label>
            </div>
            <div className="gender">
              <input type="radio" id="check-female" name="gender" />
              <label for="check-female">Female</label>
            </div>
            <div className="gender">
              <input type="radio" id="check-other" name="gender" />
              <label for="check-other">prefer not to say</label>
            </div>
          </div>
        </div>
        <div className="input-box address">
          <label>Address</label>
          <input type="text" value={formik.values.address} name='address' onChange={formik.handleChange} onBlur={formik.handleBlur} placeholder="Enter street address" required />
          {formik.touched.address && formik.errors.address && (<span className="error">{formik.errors.address}</span>)}
          <div className="column">
            <div className="select-box">
              <select>
                <option hidden>Country</option>
                <option>America</option>
                <option>Japan</option>
                <option>India</option>
                <option>Nepal</option>
              </select>
            </div>
            <input type="text" value={formik.values.city} name='city' onChange={formik.handleChange} onBlur={formik.handleBlur} placeholder="Enter your city" required />
            {formik.touched.city && formik.errors.city && (<span className="error">{formik.errors.city}</span>)}
          </div>
          <div className="column">
            <input type="text" value={formik.values.regin} name='region' onChange={formik.handleChange} onBlur={formik.handleBlur} placeholder="Enter your region" required />
            {formik.touched.region && formik.errors.region && (<span className="error">{formik.errors.region}</span>)}
            <input type="text" value={formik.values.postalCode} name='postalCode' onChange={formik.handleChange} onBlur={formik.handleBlur} placeholder="Enter your region" required />
            {formik.touched.postalCode && formik.errors.postalCode && (<span className="error">{formik.errors.postalCode}</span>)}
          </div>
        </div>
        <button>Submit</button>
      </form>
      <span className='toggle'><Link to='/login'>Already have an account?</Link></span>
    </section>
  )
}

export default Register