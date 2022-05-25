import { useAppDispatch } from 'app/config/store';
import { setHeaderElevationEffect } from 'app/shared/reducers/header';
import React, { useEffect } from 'react';
import Showcase from './Showcase';
import Testimonial from './Testimonial';

const Home = () => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(setHeaderElevationEffect(true));
    return () => {
      dispatch(setHeaderElevationEffect(false));
    };
  }, [dispatch]);

  return (
    <>
      <Showcase />
      <Testimonial />
    </>
  );
};

export default Home;
