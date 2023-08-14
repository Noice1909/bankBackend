import React from 'react'
import './Register.css'
import { Link } from 'react-router-dom'
import { useFormik } from 'formik'
import * as Yup from 'yup'
const Login = () => {
  const formik = useFormik({
    initialValues:{
      username:'',
      password:'',
    },
    validationSchema: Yup.object({
      username: Yup.string().required('Username is required'),
      password: Yup.string().required('Password is required'),
    }),
    onSubmit: (values) =>{
      console.log(values);
    }
  })
  return (
      <section className="container">
        <header>Login</header>
        <form onSubmit={formik.handleSubmit} className="form">
          <div className="input-box">
            <label>User Name</label>
            <input type="text" name='username' value={formik.values.username} onChange={formik.handleChange} onBlur={formik.handleBlur} placeholder="Enter full name" required />
            {formik.touched.username && formik.errors.username && (<span className='error'>{formik.errors.username}</span>)}
          </div>
          <div className="input-box">
            <label>Password</label>
            <input type="password" name='password' value={formik.values.password} onChange={formik.handleChange} onBlur={formik.handleBlur} placeholder="Enter Password" required />
            {formik.touched.password && formik.errors.password && (<span className='error'>{formik.errors.password}</span>)}
          </div>
          <button>Login</button>
        </form>
        <span className='toggle'><Link to='/register'>New customer?</Link></span>
      </section>
  )
}

export default Login