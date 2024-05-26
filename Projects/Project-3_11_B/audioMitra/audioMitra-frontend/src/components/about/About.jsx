const About = () => {
  return (
    <>
      <div className="text-3xl font-semibold mx-20 mt-1">About Us</div>

      <div className="flex nowrap">
        <div className="mx-20 text-justify w-1/2">
          <p className="mb-2">
            AudioMitra: AudioMitra stands as an innovative solution within the
            realm of language processing, aiming to streamline human endeavors
            through technological advancement. Functioning as a document and
            content transformation system, AudioMitra facilitates the transition
            from digital images into text through Optical Character Recognition
            (OCR), and then converting text into audio using a text-to-speech
            (TTS) system.
          </p>
          <p className="mb-2">
            Throughout the transformation process, AudioMitra allows for the
            optional collaboration between human intervention and machine
            operation. This collaborative approach ensures the authenticity and
            reliability of the transformations performed. The degree of human
            and machine involvement is tailored to meet specific workflow
            requirements, with the balance dictated by the desired level of
            accuracy and the nature of the transformations needed.
          </p>
        </div>
        <div className="w-1/2">
          <img className="w-full h-full" src="" alt="" />
        </div>
      </div>
    </>
  );
};

export default About;
